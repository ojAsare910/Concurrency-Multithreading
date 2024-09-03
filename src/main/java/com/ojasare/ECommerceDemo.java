package com.ojasare;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ECommerceDemo {
    public static void main(String[] args) throws InterruptedException {
        // 1. Concurrency vs. Multithreading demonstration
        System.out.println("1. Concurrency vs. Multithreading Demonstration");
        demonstrateConcurrencyVsMultithreading();

        // 2. Concurrent collections usage
        System.out.println("\n2. Concurrent Collections Usage");
        demonstrateConcurrentCollections();

        // 3. Performance comparison
        System.out.println("\n3. Performance Comparison");
        comparePerformance();
    }

    // 1. Concurrency vs. Multithreading
    private static void demonstrateConcurrencyVsMultithreading() throws InterruptedException {
        // Concurrent task: Process orders
        Runnable processOrders = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Processing order " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Concurrent task: Update inventory
        Runnable updateInventory = () -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Updating inventory item " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        // Demonstrate concurrency using a single thread (interleaving tasks)
        System.out.println("Concurrency (Single Thread):");
        long start = System.currentTimeMillis();
        processOrders.run();
        updateInventory.run();
        System.out.println("Time taken (Concurrency): " + (System.currentTimeMillis() - start) + "ms\n");

        // Demonstrate multithreading (parallel execution)
        System.out.println("Multithreading (Multiple Threads):");
        start = System.currentTimeMillis();
        Thread t1 = new Thread(processOrders);
        Thread t2 = new Thread(updateInventory);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("Time taken (Multithreading): " + (System.currentTimeMillis() - start) + "ms");
    }

    // 2. Concurrent Collections
    private static void demonstrateConcurrentCollections() throws InterruptedException {
        // Using ConcurrentHashMap for thread-safe product inventory
        Map<String, Integer> inventory = new ConcurrentHashMap<>();
        inventory.put("Laptop", 10);
        inventory.put("Smartphone", 20);
        inventory.put("Tablet", 15);

        // Using CopyOnWriteArrayList for thread-safe order processing
        List<String> orders = new CopyOnWriteArrayList<>();

        // Simulate multiple threads accessing and modifying collections
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Thread 1: Add new orders
        executor.submit(() -> {
            for (int i = 1; i <= 5; i++) {
                orders.add("Order " + i);
                System.out.println("Added: Order " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 2: Process orders and update inventory
        executor.submit(() -> {
            for (String order : orders) {
                System.out.println("Processing: " + order);
                inventory.computeIfPresent("Laptop", (k, v) -> v - 1);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Thread 3: Check and replenish inventory
        executor.submit(() -> {
            for (int i = 0; i < 3; i++) {
                inventory.forEach((product, quantity) -> {
                    if (quantity < 5) {
                        System.out.println("Replenishing " + product);
                        inventory.put(product, quantity + 10);
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Final Inventory: " + inventory);
        System.out.println("Final Orders: " + orders);
    }

    // 3. Performance Comparison
    private static void comparePerformance() {
        int operations = 1000000;
        int threads = 10;

        // Test with HashMap
        Map<String, Integer> hashMap = new HashMap<>();
        long hashMapTime = testMapPerformance(hashMap, operations, threads);

        // Test with ConcurrentHashMap
        Map<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long concurrentHashMapTime = testMapPerformance(concurrentHashMap, operations, threads);

        System.out.println("HashMap time: " + hashMapTime + "ms");
        System.out.println("ConcurrentHashMap time: " + concurrentHashMapTime + "ms");
    }

    private static long testMapPerformance(Map<String, Integer> map, int operations, int threads) {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            executor.submit(() -> {
                for (int j = 0; j < operations / threads; j++) {
                    String key = "key" + j;
                    map.put(key, j);
                    map.get(key);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return System.currentTimeMillis() - start;
    }

}
