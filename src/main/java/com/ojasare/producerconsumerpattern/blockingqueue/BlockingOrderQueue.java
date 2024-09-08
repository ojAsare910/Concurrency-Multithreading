package com.ojasare.producerconsumerpattern.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingOrderQueue {
    private BlockingQueue<String> orders = new LinkedBlockingQueue<>(5);

    public void placeOrder(String order) throws InterruptedException {
        orders.put(order);
        System.out.println("Order placed: " + order);
    }

    public String processOrder() throws InterruptedException {
        String order = orders.take();
        System.out.println("Order processed: " + order);
        return order;
    }
}
