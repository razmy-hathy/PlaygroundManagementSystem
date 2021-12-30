package com.playground.domain;

import constants.TicketType;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long ticketNo;

    @Enumerated
    TicketType ticketType;

    public Ticket() {
    }

    public Ticket(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Long getTicketNo() {
        return ticketNo;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getTicketNo(), ticket.getTicketNo()) && getTicketType() == ticket.getTicketType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTicketNo(), getTicketType());
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketNo=" + ticketNo +
                ", ticketType=" + ticketType +
                '}';
    }
}
