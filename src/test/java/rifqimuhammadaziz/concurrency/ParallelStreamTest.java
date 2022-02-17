package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelStreamTest {

    @Test
    void parallel() {
        Stream<Integer> stream = IntStream.range(0, 1000).boxed();

        // run parallel stream with common pool (max thread cpu)
        stream.parallel().forEach(number -> {
            System.out.println(Thread.currentThread().getName() + " : " + number);
        });
    }

    @Test
    void customPool() {
        // run parallel stream with custom pool
        var pool = new ForkJoinPool(5);

        ForkJoinTask<?> task = pool.submit(() -> {
            Stream<Integer> stream = IntStream.range(0, 1000).boxed();

            // run parallel stream with common pool (max thread cpu)
            stream.parallel().forEach(number -> {
                System.out.println(Thread.currentThread().getName() + " : " + number);
            });
        });

        task.join();
    }
}
