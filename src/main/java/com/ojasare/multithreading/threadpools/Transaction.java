package com.ojasare.multithreading.threadpools;

public class Transaction implements Runnable {
    private String transactionType;
    private double amount;

    public Transaction(String transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is processing a " + transactionType + " of $" + amount);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(transactionType + " of $" + amount + " processed by " + Thread.currentThread().getName());
    }
}
