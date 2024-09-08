package com.ojasare.producerconsumerpattern.basicproducerconsumer;

public class OrderSystem {
    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();
        Thread producerThread = new Thread(new OrderWebsite(orderQueue));
        Thread consumerThread = new Thread(new Warehouse(orderQueue));

        producerThread.start();
        consumerThread.start();
    }
}
