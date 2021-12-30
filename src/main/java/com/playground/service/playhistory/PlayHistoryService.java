package com.playground.service.playhistory;

import com.playground.domain.PlayHistory;
import com.playground.dto.VisitorCountDTO;

import java.util.List;

public interface PlayHistoryService {

   PlayHistory save(PlayHistory playHistory);

   PlayHistory retrieveLatestByChildId(long childId);

   List<VisitorCountDTO> retrieveVisitorCountByAge(long playSiteId);
}
