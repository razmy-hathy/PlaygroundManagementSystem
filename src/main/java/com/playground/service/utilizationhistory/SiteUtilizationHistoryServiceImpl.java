package com.playground.service.utilizationhistory;

import com.playground.domain.SiteUtilizationHistory;
import com.playground.repository.SiteUtilizationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteUtilizationHistoryServiceImpl implements SiteUtilizationHistoryService {

    @Autowired
    private SiteUtilizationHistoryRepository repository;

    @Override
    public SiteUtilizationHistory save(SiteUtilizationHistory siteUtilizationHistory) {
        return repository.save(siteUtilizationHistory);
    }

    @Override
    public SiteUtilizationHistory retrieveLatestHistory(long playSiteId) {
        SiteUtilizationHistory utilizationHistory = null;
        List<SiteUtilizationHistory> histories = repository.findByPlaySiteIdOrderByStartTimeDesc(playSiteId);
        if (histories != null && !histories.isEmpty()) {
            utilizationHistory = histories.get(0); // To get the latest utilization history for the given play site
        }
        return utilizationHistory;
    }

    @Override
    public List<SiteUtilizationHistory> retrieveHistories(long playSiteId) {
        return repository.findByPlaySiteIdOrderByStartTimeDesc(playSiteId);
    }
}
