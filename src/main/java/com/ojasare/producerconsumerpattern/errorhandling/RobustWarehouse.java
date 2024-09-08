package com.ojasare.producerconsumerpattern.errorhandling;

public class RobustWarehouse implements Runnable {
    private RobustOrderQueue orderQueue;

    public RobustWarehouse(RobustOrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                orderQueue.processOrder();
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
