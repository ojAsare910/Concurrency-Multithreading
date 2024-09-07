package com.ojasare.threadcontrolsanddeadlocks.deadlocks;

public class BankingSystem {
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

    static void transfer(Account from, Account to, double amount) {
        synchronized (from) {
            System.out.println("Locked account " + from.id);
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            synchronized (to) {
                System.out.println("Locked account " + to.id);
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    public static void main(String[] args) {
        final Account account1 = new Account(1, 5000);
        final Account account2 = new Account(2, 7000);

        Thread thread1 = new Thread(() -> transfer(account1, account2, 500));
        Thread thread2 = new Thread(() -> transfer(account2, account1, 300));

        thread1.start();
        thread2.start();
    }
}