package com.ojasare.producerconsumerpattern.blockingqueue;

public class BlockingWarehouse implements Runnable {
    private BlockingOrderQueue orderQueue;

    public BlockingWarehouse(BlockingOrderQueue orderQueue) {
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
