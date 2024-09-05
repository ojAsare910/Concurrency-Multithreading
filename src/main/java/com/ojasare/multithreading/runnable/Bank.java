package com.ojasare.multithreading.runnable;

public class Bank {
    public static void main(String[] args) {
        Thread teller1 = new Thread(new Transaction("Deposit", 1000));
        Thread teller2 = new Thread(new Transaction("Withdrawal", 500));

        teller1.start();
        teller2.start();
    }
}
