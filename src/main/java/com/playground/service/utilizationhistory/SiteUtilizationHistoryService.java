package com.playground.service.utilizationhistory;

import com.playground.domain.SiteUtilizationHistory;

import java.util.List;

public interface SiteUtilizationHistoryService {

    SiteUtilizationHistory save(SiteUtilizationHistory siteUtilizationHistory);

    SiteUtilizationHistory retrieveLatestHistory(long playSiteId);

    List<SiteUtilizationHistory> retrieveHistories(long playSiteId);

}
