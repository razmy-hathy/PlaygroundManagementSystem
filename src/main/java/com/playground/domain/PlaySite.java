package com.playground.domain;

import constants.PlaySiteType;

import javax.persistence.*;
import java.util.*;

@Entity
public class PlaySite {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated
    private PlaySiteType playSiteType;

    private String siteName;

    private Integer maxAllowedCount;

    @OneToMany(mappedBy = "playSite", orphanRemoval = true)
    private List<PlayHistory> playHistories = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "play_site_id")
    private Set<Child> currentPlaylist = new LinkedHashSet<>();

    @OneToMany(mappedBy = "playSite", orphanRemoval = true)
    private List<SiteUtilizationHistory> siteUtilizationHistory = new ArrayList<>();

    public PlaySite() {
    }

    public PlaySite(String siteName, PlaySiteType playSiteType, int maxAllowedCount) {
        this.siteName = siteName;
        this.playSiteType = playSiteType;
        this.maxAllowedCount = maxAllowedCount;
    }

    public Long getId() {
        return id;
    }

    public PlaySiteType getPlaySiteType() {
        return playSiteType;
    }

    public String getSiteName() {
        return siteName;
    }

    public Integer getMaxAllowedCount() {
        return maxAllowedCount;
    }

    public List<PlayHistory> getPlayHistories() {
        return playHistories;
    }

    public void setPlayHistories(List<PlayHistory> playHistories) {
        this.playHistories = playHistories;
    }

    public Set<Child> getCurrentPlaylist() {
        return currentPlaylist;
    }

    public void setCurrentPlaylist(Set<Child> currentPlaylist) {
        this.currentPlaylist = currentPlaylist;
    }

    public List<SiteUtilizationHistory> getSiteUtilizationHistory() {
        return siteUtilizationHistory;
    }

    public void setSiteUtilizationHistory(List<SiteUtilizationHistory> siteUtilizationHistory) {
        this.siteUtilizationHistory = siteUtilizationHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaySite)) return false;
        PlaySite playSite = (PlaySite) o;
        return getId().equals(playSite.getId()) && getPlaySiteType() == playSite.getPlaySiteType()
                && getSiteName().equals(playSite.getSiteName())
                && getMaxAllowedCount().equals(playSite.getMaxAllowedCount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlaySiteType(), getSiteName(), getMaxAllowedCount());
    }
}
