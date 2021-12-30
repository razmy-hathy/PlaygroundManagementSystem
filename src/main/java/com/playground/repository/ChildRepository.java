package com.playground.repository;

import com.playground.domain.Child;
import org.springframework.data.repository.CrudRepository;

public interface ChildRepository extends CrudRepository<Child, Long> {

    Child findById(long id);

    Child findByTicketTicketNo(long ticketNo);
}
