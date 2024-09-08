package com.ojasare.producerconsumerpattern.basicproducerconsumer;

import java.util.LinkedList;
import java.util.Queue;

public class OrderQueue {
    private Queue<String> orders = new LinkedList<>();
    private int capacity = 5;

    public synchronized void placeOrder(String order) throws InterruptedException {
        while (orders.size() == capacity) {
            wait();
        }
        orders.add(order);
        System.out.println("Order placed: " + order);
        notifyAll();
    }

    public synchronized String processOrder() throws InterruptedException {
        while (orders.isEmpty()) {
            wait();
        }
        String order = orders.remove();
        System.out.println("Order processed: " + order);
        notifyAll();
        return order;
    }
}
