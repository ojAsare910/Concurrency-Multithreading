package com.ojasare.producerconsumerpattern.performancebenchmark;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueOrderQueue implements OrderQueue {
    private BlockingQueue<String> orders = new LinkedBlockingQueue<>(100);

    public void placeOrder(String order) throws InterruptedException {
        orders.put(order);
    }

    public String processOrder() throws InterruptedException {
        return orders.take();
    }
}
