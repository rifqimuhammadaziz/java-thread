package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    @Test
    void test() throws InterruptedException {
        final var semaphore = new Semaphore(2); // max 2 task to run increment
        final var executor = Executors.newFixedThreadPool(100);

        for (int i = 0; i < 1000; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire(); // allow incrementing counter (only permitted 2 task)
                    Thread.sleep(1000);
                    System.out.println("Done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); // release semaphore and increment again
                }
            });
        }
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
