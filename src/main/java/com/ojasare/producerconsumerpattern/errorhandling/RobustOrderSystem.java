package com.ojasare.producerconsumerpattern.errorhandling;

public class RobustOrderSystem {
    public static void main(String[] args) {
        RobustOrderQueue orderQueue = new RobustOrderQueue();
        Thread producerThread = new Thread(new RobustOrderWebsite(orderQueue));
        Thread consumerThread = new Thread(new RobustWarehouse(orderQueue));

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
