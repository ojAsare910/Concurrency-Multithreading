package com.ojasare.producerconsumerpattern.performancebenchmark;

import java.util.concurrent.atomic.AtomicLong;

public class PerformanceBenchmark {
    private static final int NUM_ORDERS = 1_000_000;
    private static final int NUM_PRODUCERS = 5;
    private static final int NUM_CONSUMERS = 5;

    public static void main(String[] args) throws InterruptedException {
        runBenchmark("Synchronized", new SynchronizedOrderQueue());
        runBenchmark("BlockingQueue", new BlockingQueueOrderQueue());
    }

    private static void runBenchmark(String name, OrderQueue queue) throws InterruptedException {
        AtomicLong totalLatency = new AtomicLong(0);
        AtomicLong ordersProcessed = new AtomicLong(0);

        long startTime = System.currentTimeMillis();

        Thread[] producers = new Thread[NUM_PRODUCERS];
        Thread[] consumers = new Thread[NUM_CONSUMERS];

        for (int i = 0; i < NUM_PRODUCERS; i++) {
            producers[i] = new Thread(() -> {
                for (int j = 0; j < NUM_ORDERS / NUM_PRODUCERS; j++) {
                    long orderTime = System.nanoTime();
                    try {
                        queue.placeOrder("Order " + j);
                        totalLatency.addAndGet(System.nanoTime() - orderTime);
                        ordersProcessed.incrementAndGet();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (int i = 0; i < NUM_CONSUMERS; i++) {
            consumers[i] = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        queue.processOrder();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (Thread t : producers) t.start();
        for (Thread t : consumers) t.start();

        for (Thread t : producers) t.join();
        for (Thread t : consumers) t.interrupt();
        for (Thread t : consumers) t.join();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        double throughput = ordersProcessed.get() / (duration / 1000.0);
        double avgLatency = totalLatency.get() / (double) ordersProcessed.get() / 1_000_000.0;

        System.out.printf("%s Implementation:%n", name);
        System.out.printf("Throughput: %.2f orders/second%n", throughput);
        System.out.printf("Average Latency: %.3f ms%n%n", avgLatency);
    }
}
