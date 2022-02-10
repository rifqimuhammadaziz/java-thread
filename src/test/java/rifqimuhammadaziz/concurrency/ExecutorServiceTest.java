package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceTest {

    // MANAGEMENT THREAD

    @Test
    void testSingleExecutor() throws InterruptedException {
        // execute task using single thread
        var executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in Thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void testFixExecutor() throws InterruptedException {
        // execute task using fixed thread pool (min & max : 10)
        var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("Runnable in Thread : " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown(); // terminate thread if done
        executor.awaitTermination(1, TimeUnit.DAYS);
    }


}
