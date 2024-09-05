package com.ojasare.multithreading.threadsynchronization;

public class Bank {
    public static void main(String[] args) {
        BankDatabase database = new BankDatabase();

        Teller teller1 = new Teller(database, "Withdrawal", 300);
        Teller teller2 = new Teller(database, "Deposit", 700);

        teller1.start();
        teller2.start();
    }
}
