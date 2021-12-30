package com.playground.service.playsite;

import com.playground.domain.Child;
import com.playground.domain.PlayHistory;
import com.playground.domain.PlaySite;
import com.playground.domain.SiteUtilizationHistory;
import com.playground.queue.PlayGroundQueue;
import com.playground.repository.PlaySiteRepository;
import com.playground.service.child.ChildService;
import com.playground.service.playhistory.PlayHistoryService;
import com.playground.service.utilizationhistory.SiteUtilizationHistoryService;
import constants.PlaySiteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Queue;

@Service
public class PlaySiteServiceImpl implements PlaySiteService {

    private static final Logger log = LoggerFactory.getLogger(PlaySiteServiceImpl.class);

    @Autowired
    private PlaySiteRepository repository;

    @Autowired
    private ChildService childService;

    @Autowired
    private PlayHistoryService playHistoryService;

    @Autowired
    private SiteUtilizationHistoryService utilizationHistoryService;

    @Autowired
    private PlayGroundQueue ballPitQueue;

    @Autowired
    private PlayGroundQueue carouselQueue;

    @Autowired
    private PlayGroundQueue doubleSwingQueue;

    @Autowired
    private PlayGroundQueue sliderQueue;

    @Transactional
    @Override
    public void addChild(long playSiteId, Child child) {
        PlaySite playSite = repository.findById(playSiteId);
        Set currentPlayList = playSite.getCurrentPlaylist();
        int maxAllowedCount = playSite.getMaxAllowedCount();

        if (currentPlayList.size() < maxAllowedCount) {
            addToPlayList(playSite, currentPlayList, child);
        } else {
            retrieveQueue(playSite.getPlaySiteType()).put(child);
        }
    }

    @Transactional
    @Override
    public void addFromQueue(long playSiteId) {
        PlaySite playSite = repository.findById(playSiteId);
        Set currentPlayList = playSite.getCurrentPlaylist();
        int maxAllowedCount = playSite.getMaxAllowedCount();
        PlaySiteType playSiteType = playSite.getPlaySiteType();

        Child child = retrieveQueue(playSiteType).poll();
        if (child != null) {
            if (currentPlayList.size() == maxAllowedCount) {
                String msg = "Maximum allowed limit is in the playlist";
                log.info(msg);
                throw new UnsupportedOperationException(msg);
            } else {
                addToPlayList(playSite, currentPlayList, child);
            }
        } else {
            String msg = "Queue is empty. Cannot add child from the queue : " + playSiteType.name();
            log.info(msg);
            throw new UnsupportedOperationException(msg);
        }
    }

    @Override
    public void removeChild(long playSiteId, Child child) {
        PlaySite playSite = repository.findById(playSiteId);
        retrieveQueue(playSite.getPlaySiteType()).remove(child);
    }

    @Override
    public void removeAll(long playSiteId) {
        PlaySite playSite = repository.findById(playSiteId);
        retrieveQueue(playSite.getPlaySiteType()).removeAll();
    }

    @Override
    public List<PlaySite> retrieveAllPlaySites() {
        return repository.findAll();
    }

    @Override
    public PlaySite retrieveById(long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public void startPlay(long playSiteId) {
        PlaySite playSite = repository.findById(playSiteId);
        Set currentPlayList = playSite.getCurrentPlaylist();
        int maxAllowedCount = playSite.getMaxAllowedCount();

        playSite.getCurrentPlaylist().stream().forEach((child) -> {
            PlayHistory playHistory = playHistoryService.save(new PlayHistory(new Date(), child, playSite));
            child.getPlayHistories().add(playHistory);
            childService.save(child);
        });
        saveUtilizationHistory(playSite, new SiteUtilizationHistory(playSite, new Date()),
                currentPlayList, maxAllowedCount);
    }

    @Override
    public PlaySite save(PlaySite playSite) {
        return repository.save(playSite);
    }

    @Transactional
    @Override
    public void stopPlay(long playSiteId) {
        PlaySite playSite = repository.findById(playSiteId);
        Set<Child> currentPlayList = playSite.getCurrentPlaylist();
        int maxAllowedCount = playSite.getMaxAllowedCount();

        currentPlayList.stream().forEach((child) -> {
            PlayHistory playHistory = playHistoryService.retrieveLatestByChildId(child.getId());
            if (playHistory != null) {
                long startTime = playHistory.getStartTime().getTime();
                Date endTime = new Date();
                int duration = calculateDuration(startTime, endTime.getTime());
                playHistory.setDuration(duration);
                playHistory.setEndTime(endTime);
                playHistoryService.save(playHistory);
            }
        });

        SiteUtilizationHistory utilizationHistory =
                utilizationHistoryService.retrieveLatestHistory(playSite.getId());
        if (utilizationHistory != null) {
            long startTime = utilizationHistory.getStartTime().getTime();
            Date endTime = new Date();
            int duration = calculateDuration(startTime, endTime.getTime());
            utilizationHistory.setDuration(duration);
            utilizationHistory.setEndTime(endTime);
            saveUtilizationHistory(playSite, utilizationHistory, currentPlayList, maxAllowedCount);
        }

        currentPlayList.clear();
        playSite.setCurrentPlaylist(currentPlayList);
        repository.save(playSite);
    }

    @Override
    public Queue<Child> retrieveAllFromQueue(long playSiteId) {
        PlaySite playSite = repository.findById(playSiteId);
        return retrieveQueue(playSite.getPlaySiteType()).retrieveAll();
    }

    private PlayGroundQueue retrieveQueue(PlaySiteType playSiteType) {
        PlayGroundQueue queue = null;
        switch (playSiteType) {
            case BALL_PIT:
                queue = ballPitQueue;
                break;
            case CAROUSEL:
                queue = carouselQueue;
                break;
            case DOUBLE_SWING:
                queue = doubleSwingQueue;
                break;
            case SLIDER:
                queue = sliderQueue;
                break;
        }
        return queue;
    }

    private int calculateDuration(long startTime, long endTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(endTime - startTime));
        //  please note that the time difference is taken in millisecond
        // and divided over 10 here, but in real scenario it should convert into minutes.
        return calendar.get(Calendar.MILLISECOND) / 10;
    }


    private void saveUtilizationHistory(PlaySite playSite, SiteUtilizationHistory utilizationHistory,
                                        Set currentPlayList, int maxAllowedCount) {
        if (playSite.getPlaySiteType().equals(PlaySiteType.DOUBLE_SWING)) {
            if (currentPlayList.size() == maxAllowedCount) {
                utilizationHistoryService.save(utilizationHistory);
            }
        } else {
            utilizationHistoryService.save(utilizationHistory);
        }
    }

    private void addToPlayList(PlaySite playSite, Set currentPlaylist, Child child) {
        currentPlaylist.add(child);
        playSite.setCurrentPlaylist(currentPlaylist);
        repository.save(playSite);
    }
}
