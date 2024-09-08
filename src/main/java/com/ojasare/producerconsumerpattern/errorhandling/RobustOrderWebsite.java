package com.ojasare.producerconsumerpattern.errorhandling;

public class RobustOrderWebsite implements Runnable {
    private RobustOrderQueue orderQueue;

    public RobustOrderWebsite(RobustOrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void run() {
        try {
            for (int i = 1; i <= 15; i++) {
                orderQueue.placeOrder(i % 3 == 0 ? "" : "Order " + i);  // Introduce some invalid orders
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
