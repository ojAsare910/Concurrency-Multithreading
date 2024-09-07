package com.ojasare.threadcontrolsanddeadlocks.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class CustomerLoyaltyProcessor extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 1000;
    private List<Customer> customers;
    private int start, end;

    public CustomerLoyaltyProcessor(List<Customer> customers, int start, int end) {
        this.customers = customers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            return processCustomersSequentially();
        } else {
            int mid = (start + end) / 2;
            CustomerLoyaltyProcessor leftTask = new CustomerLoyaltyProcessor(customers, start, mid);
            CustomerLoyaltyProcessor rightTask = new CustomerLoyaltyProcessor(customers, mid, end);
            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();
            return leftResult + rightResult;
        }
    }

    private int processCustomersSequentially() {
        int totalPoints = 0;
        for (int i = start; i < end; i++) {
            Customer customer = customers.get(i);
            int points = calculateLoyaltyPoints(customer);
            customer.setLoyaltyPoints(points);
            totalPoints += points;
        }
        return totalPoints;
    }

    private int calculateLoyaltyPoints(Customer customer) {
        return customer.getTotalPurchases() / 100;
    }

    static class Customer {
        private int id;
        private int totalPurchases;
        private int loyaltyPoints;

        public Customer(int id, int totalPurchases) {
            this.id = id;
            this.totalPurchases = totalPurchases;
        }

        public int getTotalPurchases() {
            return totalPurchases;
        }

        public void setLoyaltyPoints(int points) {
            this.loyaltyPoints = points;
        }

        @Override
        public String toString() {
            return "Customer " + id + ": Purchases = $" + totalPurchases + ", Loyalty Points = " + loyaltyPoints;
        }
    }

    public static void main(String[] args) {
        List<Customer> customers = generateCustomerData(1000000);

        ForkJoinPool pool = new ForkJoinPool();
        CustomerLoyaltyProcessor processor = new CustomerLoyaltyProcessor(customers, 0, customers.size());

        long startTime = System.currentTimeMillis();
        int totalLoyaltyPoints = pool.invoke(processor);
        long endTime = System.currentTimeMillis();

        System.out.println("Total loyalty points: " + totalLoyaltyPoints);
        System.out.println("Processing time: " + (endTime - startTime) + " ms");

        customers.stream().limit(10).forEach(System.out::println);
    }

    private static List<Customer> generateCustomerData(int count) {
        List<Customer> customers = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            customers.add(new Customer(i, random.nextInt(10000)));
        }
        return customers;
    }
}
