package com.ojasare.synchronization;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OfficePrintQueue {
    private final Queue<String> printJobs = new LinkedList<>();
    private final int MAX_JOBS = 5;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void addPrintJob(String job) throws InterruptedException {
        lock.lock();
        try {
            while (printJobs.size() == MAX_JOBS) {
                System.out.println("Queue full. Waiting to add: " + job);
                notFull.await();
            }
            printJobs.offer(job);
            System.out.println("Added print job: " + job);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String processPrintJob() throws InterruptedException {
        lock.lock();
        try {
            while (printJobs.isEmpty()) {
                System.out.println("Queue empty. Printer waiting.");
                notEmpty.await();
            }
            String job = printJobs.poll();
            System.out.println("Processing print job: " + job);
            notFull.signal();
            return job;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        OfficePrintQueue printQueue = new OfficePrintQueue();

        Thread[] addJobThreads = new Thread[10];
        for (int i = 0; i < addJobThreads.length; i++) {
            final int jobId = i;
            addJobThreads[i] = new Thread(() -> {
                try {
                    printQueue.addPrintJob("Job " + jobId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread printerThread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    printQueue.processPrintJob();
                    Thread.sleep(100); // Simulate printing time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (Thread t : addJobThreads) {
            t.start();
        }
        printerThread.start();

        for (Thread t : addJobThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            printerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}