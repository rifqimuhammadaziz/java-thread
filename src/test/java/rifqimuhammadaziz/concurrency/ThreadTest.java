package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    void mainThread() {
        var name = Thread.currentThread().getName();
        System.out.println(name);
    }

    @Test
    void threadCreate() {
        Runnable runnable = () -> {
            System.out.println("Hello from Thread : " + Thread.currentThread().getName());
        };

        // create new thread | Asynchronous : executed code concurrently (not waiting other code to finish)
        var thread = new Thread(runnable); // Thread-0
        thread.start();

        System.out.println("Application Stopped.");
    }

    // hardcode (waiting thread sleep time manual)
    @Test
    void threadSleep() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(3_000L);
                System.out.println("Hello from Thread : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable); // Thread-0
        thread.start(); // asynchronous

        System.out.println("Application Stopped.");

        Thread.sleep(4_000L); // waiting runnable thread
    }

    // waiting other thread automatic
    @Test
    void threadJoin() throws InterruptedException{
        Runnable runnable = () -> {
            try {
                Thread.sleep(3_000L);
                System.out.println("Hello from Thread : " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable); // Thread-0

        thread.start(); // asynchronous
        System.out.println("Waiting other process...");
        thread.join(); // wait other process until done
        System.out.println("Application Stopped.");
    }

    @Test
    void threadInterruptWrong() throws InterruptedException{
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                // manual check interrupted
//                if (Thread.interrupted()) {
//                    return;
//                }

                System.out.println("Runnable : " + i);
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        var thread = new Thread(runnable); // Thread-0

        thread.start(); // asynchronous
        Thread.sleep(5_000);
        thread.interrupt();
        System.out.println("Waiting other process...");
        thread.join(); // wait other process until done
        System.out.println("Application Stopped.");
    }

    @Test
    void threadInterruptCorrect() throws InterruptedException{
        Runnable runnable = () -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("Runnable : " + i);
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    return; // break after thread interrupted
                }
            }
        };

        var thread = new Thread(runnable); // Thread-0

        thread.start(); // asynchronous
        Thread.sleep(5_000);
        thread.interrupt();
        System.out.println("Waiting other process...");
        thread.join(); // wait other process until done
        System.out.println("Application Stopped.");
    }

    @Test
    void threadName() {
        var thread = new Thread(() -> {
            System.out.println("Run in thread : " + Thread.currentThread().getName());
        });
        thread.setName("Xenosty Thread");
        thread.start();
    }

    @Test
    void threadState() throws InterruptedException {
        var thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getState());
            System.out.println("Run in thread : " + Thread.currentThread().getName());
        });

        thread.setName("Xenosty Thread");
        System.out.println(thread.getState()); // NEW
        thread.start(); // RUNNABLE
        thread.join(); // Run thread
        System.out.println(thread.getState()); // TERMINATED
    }
}
