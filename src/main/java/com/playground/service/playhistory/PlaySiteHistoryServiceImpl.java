package com.playground.service.playhistory;

import com.playground.domain.PlayHistory;
import com.playground.dto.VisitorCountDTO;
import com.playground.repository.PlayHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PlaySiteHistoryServiceImpl implements PlayHistoryService {

    @Autowired
    private PlayHistoryRepository repository;

    @Override
    public PlayHistory save(PlayHistory playHistory) {
        return repository.save(playHistory);
    }

    @Transactional
    @Override
    public PlayHistory retrieveLatestByChildId(long childId) {
        PlayHistory playHistory = null;
        List<PlayHistory> playHistories = repository.findByChildIdOrderByStartTimeDesc(childId);
        if (playHistories != null && !playHistories.isEmpty()) {
            playHistory = playHistories.get(0); // get the latest play history.
        }
        return playHistory;
    }

    @Override
    public List<VisitorCountDTO> retrieveVisitorCountByAge(long playSiteId) {
        return repository.findVisitorCountByPlaySiteIdGroupByChildAge(playSiteId);
    }

}
