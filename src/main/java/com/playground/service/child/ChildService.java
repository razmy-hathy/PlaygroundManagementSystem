package com.playground.service.child;

import com.playground.domain.Child;

public interface ChildService {

   Child save(Child child);

   Child retrieveByTicketNo(long ticketNo);
}
