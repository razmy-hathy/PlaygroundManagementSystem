package com.playground;

import com.playground.domain.Child;
import com.playground.domain.Ticket;
import com.playground.queue.PlayGroundQueue;
import constants.TicketType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ComponentScan("com.playground")
public class PlayGroundQueueTest {

    @Autowired
    private PlayGroundQueue playGroundQueue;

    @Test
    void testPlayGroundQueueSorting() {
        Child[] children = new Child[]{
                new Child("Tom", 12, new Ticket(TicketType.NORMAL)),
                new Child("Jimmy", 8, new Ticket(TicketType.VIP)),
                new Child("Peter", 11, new Ticket(TicketType.NORMAL)),
                new Child("Michael", 17, new Ticket(TicketType.NORMAL)),
                new Child("Jackson", 13, new Ticket(TicketType.NORMAL)),
                new Child("Anna", 10, new Ticket(TicketType.NORMAL)),
                new Child("Tony", 12, new Ticket(TicketType.VIP)),
                new Child("Jim", 12, new Ticket(TicketType.NORMAL)),
                new Child("Ahmed", 7, new Ticket(TicketType.VIP)),
                new Child("Nazeer", 14, new Ticket(TicketType.NORMAL)),
                new Child("Rivwan", 16, new Ticket(TicketType.NORMAL)),
                new Child("Aikido", 17, new Ticket(TicketType.NORMAL)),
                new Child("Bruce", 14, new Ticket(TicketType.NORMAL)),
                new Child("Lee", 18, new Ticket(TicketType.NORMAL)),
                new Child("Jacky", 13, new Ticket(TicketType.VIP)),
                new Child("Muru", 19, new Ticket(TicketType.NORMAL)),
                new Child("Tao", 12, new Ticket(TicketType.NORMAL)),
                new Child("Tharo", 11, new Ticket(TicketType.NORMAL)),
                new Child("Saman", 15, new Ticket(TicketType.NORMAL)),
                new Child("Rizwan", 18, new Ticket(TicketType.NORMAL)),
                new Child("Abdul", 16, new Ticket(TicketType.VIP)),
                new Child("Irshad", 19, new Ticket(TicketType.NORMAL)),
                new Child("Anton", 14, new Ticket(TicketType.NORMAL)),
                new Child("Silva", 15, new Ticket(TicketType.NORMAL)),
                new Child("Zaakir", 12, new Ticket(TicketType.VIP)),
                new Child("Nazeel", 16, new Ticket(TicketType.NORMAL)),
        };
        // put children into the queue
        for (Child child : children) {
            playGroundQueue.put(child);
        }
        // manual sorted array of children
        Child[] manualSortedChildren = new Child[]{
                new Child("Jimmy", 8, new Ticket(TicketType.VIP)),
                new Child("Tom", 12, new Ticket(TicketType.NORMAL)),
                new Child("Peter", 11, new Ticket(TicketType.NORMAL)),
                new Child("Michael", 17, new Ticket(TicketType.NORMAL)),
                new Child("Tony", 12, new Ticket(TicketType.VIP)),
                new Child("Jackson", 13, new Ticket(TicketType.NORMAL)),
                new Child("Anna", 10, new Ticket(TicketType.NORMAL)),
                new Child("Jim", 12, new Ticket(TicketType.NORMAL)),
                new Child("Ahmed", 7, new Ticket(TicketType.VIP)),
                new Child("Nazeer", 14, new Ticket(TicketType.NORMAL)),
                new Child("Rivwan", 16, new Ticket(TicketType.NORMAL)),
                new Child("Aikido", 17, new Ticket(TicketType.NORMAL)),
                new Child("Jacky", 13, new Ticket(TicketType.VIP)),
                new Child("Bruce", 14, new Ticket(TicketType.NORMAL)),
                new Child("Lee", 18, new Ticket(TicketType.NORMAL)),
                new Child("Muru", 19, new Ticket(TicketType.NORMAL)),
                new Child("Abdul", 16, new Ticket(TicketType.VIP)),
                new Child("Tao", 12, new Ticket(TicketType.NORMAL)),
                new Child("Tharo", 11, new Ticket(TicketType.NORMAL)),
                new Child("Saman", 15, new Ticket(TicketType.NORMAL)),
                new Child("Zaakir", 12, new Ticket(TicketType.VIP)),
                new Child("Rizwan", 18, new Ticket(TicketType.NORMAL)),
                new Child("Irshad", 19, new Ticket(TicketType.NORMAL)),
                new Child("Anton", 14, new Ticket(TicketType.NORMAL)),
                new Child("Silva", 15, new Ticket(TicketType.NORMAL)),
                new Child("Nazeel", 16, new Ticket(TicketType.NORMAL)),
        };
        List<Child> manualSortedList = new ArrayList<>(manualSortedChildren.length);
        for (Child child : manualSortedChildren) {
            manualSortedList.add(child);
        }
        // retrieve children from queue
        List<Child> childrenFromQueue = playGroundQueue.retrieveAll();
        // verify the queue sorting order
        assertThat(childrenFromQueue).isEqualTo(manualSortedList);
    }

}
