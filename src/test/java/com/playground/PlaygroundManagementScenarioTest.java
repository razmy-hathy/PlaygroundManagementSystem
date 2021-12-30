package com.playground;

import com.playground.domain.*;
import com.playground.dto.VisitorCountDTO;
import com.playground.service.child.ChildService;
import com.playground.service.playhistory.PlayHistoryService;
import com.playground.service.playsite.PlaySiteService;
import com.playground.service.ticket.TicketService;
import com.playground.service.utilizationhistory.SiteUtilizationHistoryService;
import constants.PlaySiteType;
import constants.TicketType;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("com.playground")
public class PlaygroundManagementScenarioTest {

    private static final Logger log = LoggerFactory.getLogger(PlaygroundManagementScenarioTest.class);

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ChildService childService;

    @Autowired
    private PlaySiteService playSiteService;

    @Autowired
    private SiteUtilizationHistoryService utilizationHistoryService;

    @Autowired
    private PlayHistoryService playHistoryService;


    @Test
    void testTicketCreate() {
        Ticket ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
    }

    @Test
    void testChildCreate() {
        Ticket ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
        Child child = childService.save(new Child("Tim", 12, ticket));
        assertThat(child).isNotNull();
        assertThat(child.getId()).isNotNull();
        assertThat(child.getTicket()).isEqualTo(ticket);
    }

    @Test
    void testPlaySitesCreate() {
        // create multiple play sites.
        PlaySite swingOne = playSiteService.save(
                new PlaySite("Swing one", PlaySiteType.DOUBLE_SWING, 2));
        assertThat(swingOne).isNotNull();
        PlaySite swingTwo = playSiteService.save(
                new PlaySite("Swing two", PlaySiteType.DOUBLE_SWING, 2));
        assertThat(swingTwo).isNotNull();
        PlaySite carousel = playSiteService.save(
                new PlaySite("Carousel", PlaySiteType.CAROUSEL, 50));
        assertThat(carousel).isNotNull();
        PlaySite ballPit = playSiteService.save(
                new PlaySite("Ball Pit", PlaySiteType.BALL_PIT, 10));
        assertThat(ballPit).isNotNull();
        PlaySite slider = playSiteService.save(new PlaySite("Slider", PlaySiteType.SLIDER, 10));
        assertThat(slider).isNotNull();

        List<PlaySite> playSites = new ArrayList<>();
        playSites.add(swingOne);
        playSites.add(swingTwo);
        playSites.add(carousel);
        playSites.add(ballPit);
        playSites.add(slider);

        List<PlaySite> playSitesRetrieved = playSiteService.retrieveAllPlaySites();
        assertThat(playSitesRetrieved).isNotNull();
        // Compare play sites.
        assertThat(playSitesRetrieved).isEqualTo(playSites);
    }

    @Test
    void testFullPlyingScenario() {
        /** create multiple play sites. **/
        PlaySite swingOne = playSiteService.save(
                new PlaySite("Swing one", PlaySiteType.DOUBLE_SWING, 2));
        assertThat(swingOne).isNotNull();
        PlaySite carousel = playSiteService.save(
                new PlaySite("Carousel", PlaySiteType.CAROUSEL, 50));
        assertThat(carousel).isNotNull();

        Ticket ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
        Child tim = childService.save(new Child("Tim", 12, ticket));
        assertThat(tim).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child tom = childService.save(new Child("Tom", 13, ticket));
        assertThat(tom).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child jim = childService.save(new Child("Jim", 15, ticket));
        assertThat(jim).isNotNull();

        ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
        Child tony = childService.save(new Child("Tony", 10, ticket));
        assertThat(tony).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child jackson = childService.save(new Child("Jackson", 12, ticket));
        assertThat(jackson).isNotNull();

        /** Test double swing. **/
        playSiteService.addChild(swingOne.getId(), tim);
        playSiteService.addChild(swingOne.getId(), tom);

        playSiteService.startPlay(swingOne.getId());
        TestHelper.waitForSeconds(2);
        playSiteService.stopPlay(swingOne.getId());

        List<PlayHistory> timPlayHistories = tim.getPlayHistories();
        assertThat(timPlayHistories).isNotNull();
        assertThat(timPlayHistories.size()).isEqualTo(1);
        PlayHistory timPlayHistory = timPlayHistories.get(0);

        List<PlayHistory> tomPlayHistories = tom.getPlayHistories();
        assertThat(tomPlayHistories).isNotNull();
        assertThat(tomPlayHistories.size()).isEqualTo(1);
        PlayHistory tomPlayHistory = tomPlayHistories.get(0);

        log.info("=======================================");
        log.info("Tim's play history in double swing");
        log.info("Child Name : " + timPlayHistory.getChild().getChildName());
        log.info("Child Age : " + timPlayHistory.getChild().getAge());
        log.info("Play Site : " + timPlayHistory.getPlaySite().getSiteName());
        log.info("Start time : " + timPlayHistory.getStartTime());
        log.info("End time : " + timPlayHistory.getEndTime());
        log.info("Duration: " + timPlayHistory.getDuration());
        log.info("=======================================");

        log.info("=======================================");
        log.info("Tom's play history in double swing");
        log.info("Child Name : " + tomPlayHistory.getChild().getChildName());
        log.info("Child Age : " + tomPlayHistory.getChild().getAge());
        log.info("Play Site : " + tomPlayHistory.getPlaySite().getSiteName());
        log.info("Start time : " + tomPlayHistory.getStartTime());
        log.info("End time : " + tomPlayHistory.getEndTime());
        log.info("Duration: " + tomPlayHistory.getDuration());
        log.info("=======================================");

        /** Site utilization history **/
        List<SiteUtilizationHistory> utilizationHistories
                = utilizationHistoryService.retrieveHistories(swingOne.getId());
        assertThat(utilizationHistories).isNotNull();
        assertThat(utilizationHistories.size()).isEqualTo(1);
        SiteUtilizationHistory utilizationHistory = utilizationHistories.get(0);

        log.info("=======================================");
        log.info("Double Swing play history after Tim and Tom played");
        log.info("Site Name : " + utilizationHistory.getPlaySite().getSiteName());
        log.info("Start time : " + utilizationHistory.getStartTime());
        log.info("End time : " + utilizationHistory.getEndTime());
        log.info("Duration : " + utilizationHistory.getDuration());
        log.info("=======================================");

        playSiteService.addChild(swingOne.getId(), jim);
        playSiteService.addChild(swingOne.getId(), jackson);

        playSiteService.startPlay(swingOne.getId());
        TestHelper.waitForSeconds(4);
        playSiteService.stopPlay(swingOne.getId());

        List<PlayHistory> jimPlayHistories = jim.getPlayHistories();
        assertThat(jimPlayHistories).isNotNull();
        assertThat(jimPlayHistories.size()).isEqualTo(1);
        PlayHistory jimPlayHistory = jimPlayHistories.get(0);

        List<PlayHistory> jacksonPlayHistories = jackson.getPlayHistories();
        assertThat(jacksonPlayHistories).isNotNull();
        assertThat(tomPlayHistories.size()).isEqualTo(1);
        PlayHistory jacksonPlayHistory = tomPlayHistories.get(0);

        log.info("=======================================");
        log.info("Jim's play history in double swing");
        log.info("Child Name : " + jimPlayHistory.getChild().getChildName());
        log.info("Child Age : " + jimPlayHistory.getChild().getAge());
        log.info("Play Site : " + jimPlayHistory.getPlaySite().getSiteName());
        log.info("Start time : " + jimPlayHistory.getStartTime());
        log.info("End time : " + jimPlayHistory.getEndTime());
        log.info("Duration: " + jimPlayHistory.getDuration());
        log.info("=======================================");

        log.info("=======================================");
        log.info("Jackson's play history in double swing");
        log.info("Child Name : " + jacksonPlayHistory.getChild().getChildName());
        log.info("Child Age : " + jacksonPlayHistory.getChild().getAge());
        log.info("Play Site : " + jacksonPlayHistory.getPlaySite().getSiteName());
        log.info("Start time : " + jacksonPlayHistory.getStartTime());
        log.info("End time : " + jacksonPlayHistory.getEndTime());
        log.info("Duration: " + jacksonPlayHistory.getDuration());
        log.info("=======================================");

        /** Since double swing played twice with 2 kids in each time, the site has two utilization history **/
        utilizationHistories
                = utilizationHistoryService.retrieveHistories(swingOne.getId());
        assertThat(utilizationHistories).isNotNull();
        assertThat(utilizationHistories.size()).isEqualTo(2);
        for (SiteUtilizationHistory utilizationHistory1 : utilizationHistories) {
            log.info("=======================================");
            log.info("Double Swing play history");
            log.info("Site Name : " + utilizationHistory1.getPlaySite().getSiteName());
            log.info("Start time : " + utilizationHistory1.getStartTime());
            log.info("End time : " + utilizationHistory1.getEndTime());
            log.info("Duration : " + utilizationHistory1.getDuration());
            log.info("=======================================");
        }

        /** Add kids to carousel site for play **/
        playSiteService.addChild(carousel.getId(), tim);
        playSiteService.addChild(carousel.getId(), tom);
        playSiteService.addChild(carousel.getId(), jim);
        playSiteService.addChild(carousel.getId(), tony);
        playSiteService.addChild(carousel.getId(), jackson);

        playSiteService.startPlay(carousel.getId());
        TestHelper.waitForSeconds(5);
        playSiteService.stopPlay(carousel.getId());

        /** Since Jackson played in both double swing and carousel, he has two histories **/
        jacksonPlayHistories = jackson.getPlayHistories();
        assertThat(jacksonPlayHistories).isNotNull();
        assertThat(tomPlayHistories.size()).isEqualTo(2);
        for (PlayHistory playHistory : jacksonPlayHistories) {
            log.info("=======================================");
            log.info("Jackson's play history in carousel");
            log.info("Child Name : " + playHistory.getChild().getChildName());
            log.info("Child Age : " + playHistory.getChild().getAge());
            log.info("Play Site : " + playHistory.getPlaySite().getSiteName());
            log.info("Start time : " + playHistory.getStartTime());
            log.info("End time : " + playHistory.getEndTime());
            log.info("Duration: " + playHistory.getDuration());
            log.info("=======================================");
        }

        /** Visitors count by age group **/
        List<VisitorCountDTO> visitorsCount = playHistoryService.retrieveVisitorCountByAge(swingOne.getId());
        log.info("=======================================");
        log.info("Visitors Count by age groups for site : " + swingOne.getSiteName());
        for (VisitorCountDTO visitorCount : visitorsCount) {
            log.info("Age group : " + visitorCount.getAgeGroup() + " Count : " + visitorCount.getVisitorCount());
        }
        log.info("=======================================");
    }

    @Test
    void testFullPlyingScenarioWithQueue() {
        PlaySite ballPit = playSiteService.save(
                new PlaySite("Ball Pit", PlaySiteType.BALL_PIT, 3));
        assertThat(ballPit).isNotNull();

        Ticket ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
        Child tim = childService.save(new Child("Tim", 12, ticket));
        assertThat(tim).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child tom = childService.save(new Child("Tom", 13, ticket));
        assertThat(tom).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child jim = childService.save(new Child("Jim", 15, ticket));
        assertThat(jim).isNotNull();

        ticket = ticketService.save(TicketType.VIP);
        assertThat(ticket).isNotNull();
        Child tony = childService.save(new Child("Tony", 10, ticket));
        assertThat(tony).isNotNull();

        ticket = ticketService.save(TicketType.NORMAL);
        assertThat(ticket).isNotNull();
        Child jackson = childService.save(new Child("Jackson", 12, ticket));
        assertThat(jackson).isNotNull();

        /** Add children to ball pit site as it is only three children rest two were places in the queue **/
        playSiteService.addChild(ballPit.getId(), tim);
        playSiteService.addChild(ballPit.getId(), tom);
        playSiteService.addChild(ballPit.getId(), jim);
        playSiteService.addChild(ballPit.getId(), tony);
        playSiteService.addChild(ballPit.getId(), jackson);

        Set currentPlayList = ballPit.getCurrentPlaylist();
        assertThat(currentPlayList).isNotNull();
        assertThat(currentPlayList.size()).isEqualTo(3);
        /** Contain below kids in the current play list **/
        assertThat(currentPlayList.contains(tim)).isTrue();
        assertThat(currentPlayList.contains(tom)).isTrue();
        assertThat(currentPlayList.contains(jim)).isTrue();
        /** Not contain below kids in the current play list **/
        assertThat(currentPlayList.contains(tony)).isFalse();
        assertThat(currentPlayList.contains(jackson)).isFalse();

        /** Play with Tim,Tom and Jim **/
        playSiteService.startPlay(ballPit.getId());
        TestHelper.waitForSeconds(5);
        playSiteService.stopPlay(ballPit.getId());

        /** Add remaining kids from queue **/
        playSiteService.addFromQueue(ballPit.getId());
        playSiteService.addFromQueue(ballPit.getId());

        currentPlayList = ballPit.getCurrentPlaylist();
        assertThat(currentPlayList).isNotNull();
        assertThat(currentPlayList.size()).isEqualTo(2);
        /** Contain below kids in the current play list **/
        assertThat(currentPlayList.contains(tony)).isTrue();
        assertThat(currentPlayList.contains(jackson)).isTrue();

        /** Play with Tony and Jackson **/
        playSiteService.startPlay(ballPit.getId());
        TestHelper.waitForSeconds(5);
        playSiteService.stopPlay(ballPit.getId());
    }
}
