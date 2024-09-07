package com.ojasare.threadcontrolsanddeadlocks.threadinterruption;

public class FileDownloader implements Runnable {

    @Override
    public void run() {
        System.out.println("Starting file download...");
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                System.out.println("Downloading...");
            } catch (InterruptedException e) {
                System.out.println("Download interrupted");
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Download stopped");
    }

    public static void main(String[] args) throws InterruptedException {
        FileDownloader downloader = new FileDownloader();
        Thread downloaderThread = new Thread(downloader);
        downloaderThread.start();

        Thread.sleep(5000);
        downloaderThread.interrupt();
        downloaderThread.join();
    }
}
