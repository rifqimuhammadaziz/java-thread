package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    // data exchange between threads
    // if one of the threads is not receiving, exchanger will wait until has receiver thread

    @Test
    void test() throws InterruptedException {
        final var exchanger = new Exchanger<String>();
        final var executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            try {
                System.out.println("Thread 1 : Send : First");
                Thread.sleep(1000);
                var result = exchanger.exchange("First"); // send data
                System.out.println("Thread 1 : Receive : " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                System.out.println("Thread 2 : Send : Second");
                Thread.sleep(2000 );
                var result = exchanger.exchange("Second"); // send data
                System.out.println("Thread 2 : Receive : " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
