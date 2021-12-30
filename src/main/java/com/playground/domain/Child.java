package com.playground.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String childName;

    private Integer age;

    @OneToOne
    @JoinColumn(name = "ticket_ticket_no")
    private Ticket ticket;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayHistory> playHistories = new ArrayList<>();

    public Child() {
    }

    public Child(String name, int age, Ticket ticket) {
        this.childName = name;
        this.age = age;
        this.ticket = ticket;
    }

    public Long getId() {
        return id;
    }

    public String getChildName() {
        return childName;
    }

    public Integer getAge() {
        return age;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public List<PlayHistory> getPlayHistories() {
        return playHistories;
    }

    public void setPlayHistories(List<PlayHistory> playHistories) {
        this.playHistories = playHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child)) return false;
        Child child = (Child) o;
        return Objects.equals(getId(), child.getId()) && getChildName().equals(child.getChildName())
                && getAge().equals(child.getAge()) && getTicket().equals(child.getTicket());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getChildName(), getAge(), getTicket());
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", childName='" + childName + '\'' +
                ", age=" + age +
                ", ticket=" + ticket.toString() +
                '}';
    }
}