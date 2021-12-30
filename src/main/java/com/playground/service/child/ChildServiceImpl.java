package com.playground.service.child;

import com.playground.domain.Child;
import com.playground.repository.ChildRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildServiceImpl implements ChildService {

    @Autowired
    private ChildRepository childRepository;

    @Override
    public Child save(Child child) {
        return childRepository.save(child);
    }

    @Override
    public Child retrieveByTicketNo(long ticketNo) {
        return childRepository.findByTicketTicketNo(ticketNo);
    }
}
