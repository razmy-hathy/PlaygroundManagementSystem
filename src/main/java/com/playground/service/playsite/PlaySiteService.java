package com.playground.service.playsite;

import com.playground.domain.Child;
import com.playground.domain.PlaySite;

import java.util.List;
import java.util.Queue;

public interface PlaySiteService {

    void addChild(long playSiteId, Child Child);

    void addFromQueue(long playSiteId);

    void removeChild(long playSiteId, Child child);

    void removeAll(long playSiteId);

    List<PlaySite> retrieveAllPlaySites();

    Queue<Child> retrieveAllFromQueue(long playSiteId);

    PlaySite retrieveById(long id);

    void startPlay(long playSiteId);

    PlaySite save(PlaySite playSite);

    void stopPlay(long playSiteId);

}
