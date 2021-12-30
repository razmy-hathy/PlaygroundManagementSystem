package com.playground.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleReport {

    /**
     * This fires every 4 hours to generate report and send it by mail
     * to investor.
     */
    @Scheduled(cron = "0 */4 * * *")
    public void takeSnapShot() {
        /**
         * Code to generate report and send it by mail to investor.
         */
    }
}