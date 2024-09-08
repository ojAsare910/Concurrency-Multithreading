package com.ojasare.producerconsumerpattern.blockingqueue;

public class BlockingQueueOrderSystem {
    public static void main(String[] args) {
        BlockingOrderQueue orderQueue = new BlockingOrderQueue();
        Thread producerThread = new Thread(new BlockingOrderWebsite(orderQueue));
        Thread consumerThread = new Thread(new BlockingWarehouse(orderQueue));

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
