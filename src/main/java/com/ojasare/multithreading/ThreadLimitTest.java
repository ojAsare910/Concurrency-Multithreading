package com.ojasare.multithreading;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

public class ThreadLimitTest {
    public static void main(String[] args) {
        var threadCount = new AtomicInteger(0);
        try {
            while(true) {
                var thread = new Thread(() -> {
                    threadCount.incrementAndGet();
                    LockSupport.park();
                });
                thread.start();
                System.out.println("Reached threshold of " + threadCount.get() + " threads");
            }
        } catch (OutOfMemoryError error) {
            System.out.println("Reached threshold of " + threadCount.get() + " threads");
            error.printStackTrace();
        }
    }
}
