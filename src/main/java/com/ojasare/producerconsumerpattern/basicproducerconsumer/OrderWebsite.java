package com.ojasare.producerconsumerpattern.basicproducerconsumer;

public class OrderWebsite implements Runnable {
    private OrderQueue orderQueue;

    public OrderWebsite(OrderQueue orderQueue) {
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
