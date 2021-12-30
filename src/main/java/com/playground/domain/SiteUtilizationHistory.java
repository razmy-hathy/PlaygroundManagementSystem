package com.playground.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SiteUtilizationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "play_site_id")
    private PlaySite playSite;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    private Integer duration;

    public SiteUtilizationHistory() {
    }

    public SiteUtilizationHistory(PlaySite playSite, Date startTime) {
        this.playSite = playSite;
        this.startTime = startTime;
    }

    public PlaySite getPlaySite() {
        return playSite;
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
}
