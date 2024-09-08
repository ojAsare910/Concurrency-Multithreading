package com.ojasare.producerconsumerpattern.performancebenchmark;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedOrderQueue implements OrderQueue {
    private Queue<String> orders = new LinkedList<>();
    private int capacity = 100;

    public synchronized void placeOrder(String order) throws InterruptedException {
        while (orders.size() == capacity) {
            wait();
        }
        orders.add(order);
        notifyAll();
    }

    public synchronized String processOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        String order = orders.remove();
        notifyAll();
        return order;
    }
}
