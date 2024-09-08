package com.ojasare.producerconsumerpattern.basicproducerconsumer;

public class Warehouse implements Runnable {
    private OrderQueue orderQueue;

    public Warehouse(OrderQueue orderQueue) {
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
