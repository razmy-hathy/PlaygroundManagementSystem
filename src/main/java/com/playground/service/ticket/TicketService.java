package com.playground.service.ticket;

import com.playground.domain.Ticket;
import constants.TicketType;

import java.util.List;

public interface TicketService {

    Ticket save(TicketType ticketType);

    Ticket retrieveTicketById(long id);

    List<Ticket> retrieveAlL();
}
