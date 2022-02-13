package rifqimuhammadaziz.concurrency.ConcurrentCollection;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {

    @Test
    void arrayBlockingQueue() throws InterruptedException {
        final var queue = new ArrayBlockingQueue<String>(5); // capacity of queue
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data"); // put data to max capacity
                    System.out.println("Finish Put Data.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.take(); // take data
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void linkedBlockingQueue() throws InterruptedException {
        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("Data"); // put data
                    System.out.println("Finish Put Data.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.take(); // take data every 2s
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void priorityBlockingQueue() throws InterruptedException {
        final var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder()); // descending
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                queue.put(index); // put data
                System.out.println("Finish Put Data.");
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.take(); // take data
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void delayQueue() throws InterruptedException {
        final var queue = new DelayQueue<ScheduledFuture<String>>();
        final var executor = Executors.newFixedThreadPool(20);
        final var executorScheduled = Executors.newScheduledThreadPool(10);

        for (int i = 1; i <= 10; i++) {
            final var index = i;
            // schedule to put data in 1s 2s 3s ... 10s
            queue.put(executorScheduled.schedule(() -> "Data " + index, i, TimeUnit.SECONDS));
        }

        executor.execute(() -> {
            while (true) {
                try {
                    var value = queue.take(); // take data if data exists
                    System.out.println("Receive data : " + value.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void synchronousQueue() throws InterruptedException {
        final var queue = new SynchronousQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.put("Data - " + index); // put data
                    System.out.println("Finish Put Data : " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.take(); // take data
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    // LIFO (Last In First Out) / STACK
    @Test
    void blockingDeque() throws InterruptedException {
        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            try {
                queue.putLast("Data - " + index); // put data
                System.out.println("Finish Put Data : " + index);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.takeLast(); // take data
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    @Test
    void transferQueue() throws InterruptedException {
        final var queue = new LinkedTransferQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        // thread put data
        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.transfer("Data - " + index); // put data
                    System.out.println("Finish Put Data : " + index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // thread take data
        executor.execute(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    var value = queue.take(); // take data
                    System.out.println("Receive data : " + value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.DAYS);
    }
}
