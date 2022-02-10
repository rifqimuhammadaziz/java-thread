package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Random random = new Random();

    public Future<String> getValue() {
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Xenosty");
            } catch (InterruptedException e) {
                future.completeExceptionally(e); // send error if any
            }
        });
        return future;
    }

    public CompletableFuture<String> getValue2() {
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Rifqi Muhammad Aziz");
            } catch (InterruptedException e) {
                future.completeExceptionally(e); // send error if any
            }
        });
        return future;
    }

    @Test
    void create() throws ExecutionException, InterruptedException {
        Future<String> future = getValue();
        System.out.println(future.get());
    }

    private void execute(CompletableFuture<String> future, String value) {
        executorService.execute(() -> {
            try {
                Thread.sleep(1000 + random.nextInt(5000));
                future.complete(value);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
    }

    public Future<String> getFastest() {
        CompletableFuture<String> future = new CompletableFuture<>();

        execute(future, "Thread 1");
        execute(future, "Thread 2");
        execute(future, "Thread 3");
        execute(future, "Thread 4");
        execute(future, "Thread 5");

        return future;
    }

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }

    @Test
    void completionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = getValue2();

        CompletableFuture<String[]> completableFuture = future.thenApply(string -> string.toUpperCase())
                .thenApply(string -> string.split(" "));

        String[] strings = completableFuture.get();
        for (var value : strings) {
            System.out.println(value);
        }

    }
}
