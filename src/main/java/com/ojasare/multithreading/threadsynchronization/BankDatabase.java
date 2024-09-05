package com.ojasare.multithreading.threadsynchronization;

public class BankDatabase {
    private double totalBalance = 5000;

    public synchronized void updateBalance(String transactionType, double amount) {
        if (transactionType.equals("Withdrawal")) {
            totalBalance -= amount;
        } else if (transactionType.equals("Deposit")) {
            totalBalance += amount;
        }
        System.out.println(transactionType + " of $" + amount + " processed. New balance: $" + totalBalance);
    }
}