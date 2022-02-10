package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ThreadCommunicationTest {

    private String message = null;

    @Test
    void manual() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            while (message == null) { // need more CPU resource & still run even looping is interrupted
                // wait data 'message' is exists
            }
            // print if message is already exist
            System.out.println(message);
        });

        // set message
        Thread thread2 = new Thread(() -> {
            message = "New Message!!!";
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    void waitNotify() throws InterruptedException {
        final var lock = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // unlock thread & wait until notify
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // set message
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                message = "New Message!!!";
                lock.notify();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    @Test
    void waitNotifyAll() throws InterruptedException {
        final var lock = new Object();

        // waiting thread
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // unlock thread & wait until notify
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // waiting thread
        Thread thread3 = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // set message
        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                message = "New Message!!!";
                lock.notifyAll(); // all waiting thread allowed to runs
            }
        });

        thread1.start(); // waiting thread
        thread3.start(); // waiting thread

        // notify thread
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
