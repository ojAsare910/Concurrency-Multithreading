package com.ojasare.multithreading.threadpools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bank {
    public static void main(String[] args) {
        ExecutorService tellerPool = Executors.newFixedThreadPool(4);

        tellerPool.submit(new Transaction("Deposit", 1000));
        tellerPool.submit(new Transaction("Withdrawal", 500));
        tellerPool.submit(new Transaction("Transfer", 300));
        tellerPool.submit(new Transaction("Deposit", 800));
        tellerPool.submit(new Transaction("Withdrawal", 200));

        tellerPool.shutdown();
    }
}
