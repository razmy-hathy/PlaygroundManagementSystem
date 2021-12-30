package com.playground.dto;


public class VisitorCountDTO {
    private long visitorCount;
    private int ageGroup;

    public VisitorCountDTO(long visitorCount, int ageGroup) {
        this.visitorCount = visitorCount;
        this.ageGroup = ageGroup;
    }

    public long getVisitorCount() {
        return visitorCount;
    }

    public int getAgeGroup() {
        return ageGroup;
    }
}
