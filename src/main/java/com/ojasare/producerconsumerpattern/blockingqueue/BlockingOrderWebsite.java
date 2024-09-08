package com.ojasare.producerconsumerpattern.blockingqueue;

public class BlockingOrderWebsite implements Runnable {
    private BlockingOrderQueue orderQueue;

    public BlockingOrderWebsite(BlockingOrderQueue orderQueue) {
        this.orderQueue = orderQueue;
    }

    public void run() {
        try {
            for (int i = 1; i <= 10; i++) {
                orderQueue.placeOrder("Order " + i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
