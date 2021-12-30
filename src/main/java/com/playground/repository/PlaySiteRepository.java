package com.playground.repository;

import com.playground.domain.PlaySite;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlaySiteRepository extends CrudRepository<PlaySite, Long> {

    PlaySite findById(long id);

    List<PlaySite> findAll();

}
