package com.playground.repository;

import com.playground.domain.SiteUtilizationHistory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SiteUtilizationHistoryRepository extends CrudRepository<SiteUtilizationHistory,Long> {

    List<SiteUtilizationHistory> findAll();

    List<SiteUtilizationHistory> findByPlaySiteIdOrderByStartTimeDesc(long playSiteId);
}
