package com.playground.repository;

import com.playground.domain.PlayHistory;
import com.playground.dto.VisitorCountDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayHistoryRepository extends CrudRepository<PlayHistory, Long> {

    PlayHistory findById(long id);

    List<PlayHistory> findByChildIdOrderByStartTimeDesc(long childId);

    @Query("select new com.playground.dto.VisitorCountDTO(count(*),h.child.age) " +
            "from PlayHistory as h where h.playSite.id = :playSiteId group by h.child.age")
    List<VisitorCountDTO> findVisitorCountByPlaySiteIdGroupByChildAge(long playSiteId);
}
