package com.ojasare.multithreading.threadlifecycle;

public class Bank {
    public static void main(String[] args) {
        Teller teller1 = new Teller("Withdrawal", 300);
        Teller teller2 = new Teller("Deposit", 700);

        System.out.println("Tellers are ready to start processing transactions.");

        teller1.start();
        teller2.start();

        try {
            teller1.join();
            teller2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All transactions are completed!");
    }
}
