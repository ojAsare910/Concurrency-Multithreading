package com.ojasare.multithreading.thread;

public class Bank {
    public static void main(String[] args) {
        Teller teller1 = new Teller("Deposit", 1500);
        Teller teller2 = new Teller("Transfer", 200);

        teller1.start();
        teller2.start();
    }
}