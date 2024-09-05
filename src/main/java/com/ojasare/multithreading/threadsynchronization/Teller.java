package com.ojasare.multithreading.threadsynchronization;

public class Teller extends Thread {
    private BankDatabase database;
    private String transactionType;
    private double amount;

    public Teller(BankDatabase database, String transactionType, double amount) {
        this.database = database;
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
        database.updateBalance(transactionType, amount);
    }
}
