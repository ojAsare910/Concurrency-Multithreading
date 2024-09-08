package com.ojasare.producerconsumerpattern.performancebenchmark;

public interface OrderQueue {
    void placeOrder(String order) throws InterruptedException;
    String processOrder() throws InterruptedException;
}
