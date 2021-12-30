package com.playground.repository;

import com.playground.domain.Ticket;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Ticket findById(long id);

    List<Ticket> findAll();
}
