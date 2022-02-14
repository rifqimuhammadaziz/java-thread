package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ThreadLocalRandomTest {

    // same with ThreadLocal but specifically for create random number (to handle race condition)

    @Test
    void test() throws InterruptedException {
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    var number = ThreadLocalRandom.current().nextInt(); // create random number
                    System.out.println(number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void stream() throws InterruptedException {
        final var executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            ThreadLocalRandom.current().ints(1000, 0, 1000)
                    .forEach(System.out::println);
        });

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
