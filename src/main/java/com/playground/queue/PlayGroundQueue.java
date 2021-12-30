package com.playground.queue;

import com.playground.domain.Child;
import constants.TicketType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class PlayGroundQueue {
    private static final Logger log = LoggerFactory.getLogger(PlayGroundQueue.class);

    TicketType lastOutType = null;
    int normalTicketOutCount = 0;
    private Queue<Child> normalQueue = new LinkedList<>();
    private Queue<Child> vipQueue = new LinkedList<>();

    public Child peek() {
        Child child = null;
        if (lastOutType == null) {
            if (!vipQueue.isEmpty()) {
                child = peekVipQueue();
            } else if (!normalQueue.isEmpty()) {
                child = peekNormalQueue();
            }
        } else {
            if (lastOutType == TicketType.VIP || (lastOutType == TicketType.NORMAL && normalTicketOutCount < 3)) {
                if (!normalQueue.isEmpty()) {
                    child = peekNormalQueue();
                } else if (!vipQueue.isEmpty()) {
                    child = peekVipQueue();
                }
            } else {
                if (!vipQueue.isEmpty()) {
                    child = peekVipQueue();
                } else if (!normalQueue.isEmpty()) {
                    child = peekNormalQueue();
                }
            }
        }
        log.info("Peeked a child from the queue" + child);
        return child;
    }

    public Child poll() {
        Child child = null;
        if (lastOutType == null) {
            if (!vipQueue.isEmpty()) {
                child = pollVipQueue();
            } else if (!normalQueue.isEmpty()) {
                child = pollNormalQueue();
            }
        } else {
            if (lastOutType == TicketType.VIP || (lastOutType == TicketType.NORMAL && normalTicketOutCount < 3)) {
                if (!normalQueue.isEmpty()) {
                    child = pollNormalQueue();
                } else if (!vipQueue.isEmpty()) {
                    child = pollVipQueue();
                }
            } else {
                if (!vipQueue.isEmpty()) {
                    child = pollVipQueue();
                } else if (!normalQueue.isEmpty()) {
                    child = pollNormalQueue();
                }
            }
        }
        log.info("Polled a child :" + child);
        return child;
    }

    private Child pollNormalQueue() {
        lastOutType = TicketType.NORMAL;
        normalTicketOutCount++;
        return normalQueue.poll();
    }

    private Child pollVipQueue() {
        lastOutType = TicketType.VIP;
        normalTicketOutCount = 0;
        return vipQueue.poll();
    }

    private Child peekNormalQueue() {
        lastOutType = TicketType.NORMAL;
        normalTicketOutCount++;
        return normalQueue.peek();
    }

    private Child peekVipQueue() {
        lastOutType = TicketType.VIP;
        normalTicketOutCount = 0;
        return vipQueue.peek();
    }

    public void put(Child child) {
        if (child.getTicket().getTicketType().equals(TicketType.VIP)) {
            vipQueue.add(child);
        } else {
            normalQueue.add(child);
        }
    }

    public void remove(Child child) {
        if (child.getTicket().equals(TicketType.VIP)) {
            vipQueue.remove(child);
        } else {
            normalQueue.remove(child);
        }
    }

    public void removeAll() {
        vipQueue.clear();
        normalQueue.clear();
        lastOutType = null;
    }

    public Queue<Child> retrieveAll() {
        Queue<Child> children = new LinkedList<>();
        while (!vipQueue.isEmpty() || !normalQueue.isEmpty()) {
            children.add(poll());
        }
        return children;
    }
}
