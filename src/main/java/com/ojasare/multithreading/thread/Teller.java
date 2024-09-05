package com.ojasare.multithreading.thread;

public class Teller extends Thread {
    private String transactionType;
    private double amount;

    public Teller(String transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
    }

    @Override
    public void run() {
        System.out.println(getName() + " is processing a " + transactionType + " of $" + amount);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(transactionType + " of $" + amount + " processed by " + getName());
    }
}