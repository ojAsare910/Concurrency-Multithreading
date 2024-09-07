package com.ojasare.threadcontrolsanddeadlocks.deadlocks;

public class BankingSystemSafe {
    static class Account {
        private int id;
        private double balance;

        public Account(int id, double balance) {
            this.id = id;
            this.balance = balance;
        }

        void withdraw(double amount) {
            balance -= amount;
        }

        void deposit(double amount) {
            balance += amount;
        }
    }

    static void transferSafe(Account from, Account to, double amount) {
        Account firstLock, secondLock;
        if (from.id < to.id) {
            firstLock = from;
            secondLock = to;
        } else {
            firstLock = to;
            secondLock = from;
        }

        synchronized (firstLock) {
            System.out.println("Locked account " + firstLock.id);
            synchronized (secondLock) {
                System.out.println("Locked account " + secondLock.id);
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public static void main(String[] args) {
        final Account account1 = new Account(1, 5000);
        final Account account2 = new Account(2, 7000);

        Thread thread1 = new Thread(() -> transferSafe(account1, account2, 500));
        Thread thread2 = new Thread(() -> transferSafe(account2, account1, 300));

        thread1.start();
        thread2.start();
    }
}