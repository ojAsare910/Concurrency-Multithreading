package com.ojasare.multithreading.threadlifecycle;

public class Teller extends Thread {
    private String transactionType;
    private double amount;

    public Teller(String transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        System.out.println(getName() + " is in the NEW state (teller is ready for the transaction)");
    }

    @Override
    public void run() {
        System.out.println(getName() + " is in the RUNNABLE state (processing " + transactionType + " of $" + amount + ")");

        try {
            System.out.println(getName() + " is in the TIMED_WAITING state (waiting for approval)");
            Thread.sleep(2000);

            synchronized (this) {
                System.out.println(getName() + " is in the BLOCKED state (waiting to access the bank's database)");
                System.out.println(getName() + " is processing " + transactionType + " of $" + amount);
                Thread.sleep(2000);
            }

            System.out.println(getName() + " is in the TERMINATED state (transaction complete)");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
