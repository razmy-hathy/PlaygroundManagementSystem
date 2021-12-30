package com.playground.service.ticket;

import com.playground.domain.Ticket;
import com.playground.repository.TicketRepository;
import constants.TicketType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public Ticket save(TicketType ticketType) {
        return ticketRepository.save(new Ticket(ticketType));
    }

    @Override
    public Ticket retrieveTicketById(long id) {
        return ticketRepository.findById(id);
    }

    @Override
    public List<Ticket> retrieveAlL() {
        return ticketRepository.findAll();
    }

}
