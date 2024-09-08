package com.ojasare.producerconsumerpattern.errorhandling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RobustOrderQueue {
    private BlockingQueue<String> orders = new LinkedBlockingQueue<>(100);

    public void placeOrder(String order) throws InterruptedException {
        try {
            if (order == null || order.isEmpty()) {
                throw new IllegalArgumentException("Invalid order");
            }
            boolean success = orders.offer(order, 5, TimeUnit.SECONDS);
            if (!success) {
                throw new TimeoutException("Order queue is full");
            }
            System.out.println("Order placed: " + order);
        } catch (IllegalArgumentException | TimeoutException e) {
            System.err.println("Error placing order: " + e.getMessage());
        }
    }

    public String processOrder() throws InterruptedException {
        try {
            String order = orders.poll(5, TimeUnit.SECONDS);
            if (order == null) {
                throw new TimeoutException("No orders available");
            }
            System.out.println("Order processed: " + order);
            return order;
        } catch (TimeoutException e) {
            System.err.println("Error processing order: " + e.getMessage());
            return null;
        }
    }
}
