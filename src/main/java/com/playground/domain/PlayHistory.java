package com.playground.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class PlayHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name = "play_site_id")
    private PlaySite playSite;

    public PlayHistory() {
    }

    public PlayHistory(Date startTime, Child child, PlaySite playSite) {
        this.startTime = startTime;
        this.child = child;
        this.playSite = playSite;
    }

    public Long getId() {
        return id;
    }

    public Child getChild() {
        return child;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public PlaySite getPlaySite() {
        return playSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlayHistory)) return false;
        PlayHistory that = (PlayHistory) o;
        return startTime.equals(that.startTime) && Objects.equals(endTime, that.endTime)
                && Objects.equals(duration, that.duration)
                && getChild().equals(that.getChild()) && getPlaySite().equals(that.getPlaySite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, duration, getChild(), getPlaySite());
    }
}