package com.ojasare.synchronization;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WebServerHitCounter {
    private final AtomicLong hits = new AtomicLong(0);

    public void incrementHits() {
        hits.incrementAndGet();
    }

    public long getHits() {
        return hits.get();
    }

    public static void main(String[] args) throws InterruptedException {
        WebServerHitCounter counter = new WebServerHitCounter();
        ExecutorService executor = Executors.newFixedThreadPool(10); // Simulate 10 concurrent users

        for (int i = 0; i < 1000; i++) {
            executor.submit(counter::incrementHits);
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("Total hits: " + counter.getHits());
    }
}