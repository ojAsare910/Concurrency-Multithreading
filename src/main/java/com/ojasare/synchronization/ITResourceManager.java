package com.ojasare.synchronization;

public class ITResourceManager {
    private static class Resource {
        private final String name;
        Resource(String name) { this.name = name; }
        public String getName() { return name; }
    }

    private static final Resource DATABASE = new Resource("Database");
    private static final Resource FILE_SYSTEM = new Resource("File System");

    private static void useResources(Resource first, Resource second, String department) {
        synchronized (first) {
            System.out.println(department + " acquired " + first.getName());
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (second) {
                System.out.println(department + " acquired " + second.getName());
                System.out.println(department + " is using both resources");
            }
        }
    }

    public static void main(String[] args) {
        Thread dataDept = new Thread(() -> {
            useResources(DATABASE, FILE_SYSTEM, "Data Department");
        });

        Thread infraDept = new Thread(() -> {
            useResources(DATABASE, FILE_SYSTEM, "Infrastructure Department");
        });

        dataDept.start();
        infraDept.start();
    }
}
