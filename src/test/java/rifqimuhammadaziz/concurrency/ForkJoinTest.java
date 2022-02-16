package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinTest {

    @Test
    void create() {
        var forkJoinPool1 = ForkJoinPool.commonPool(); // core processor of computer
        var forkJoinPool2 = new ForkJoinPool(5);

        var executor1 = Executors.newWorkStealingPool(); // core processor of computer
        var executor2 = Executors.newWorkStealingPool(5);
    }

    @Test
    void recursiveAction() throws InterruptedException {
        var pool = ForkJoinPool.commonPool();
//        var pool = new ForkJoinPool(5);
        List<Integer> integers = IntStream.range(0, 100).boxed().collect(Collectors.toList());

        var task = new SimpleForkJoinTask(integers);
        pool.execute(task);

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.DAYS);
    }

    public static class SimpleForkJoinTask extends RecursiveAction {

        private List<Integer> integers;

        public SimpleForkJoinTask(List<Integer> integers) {
            this.integers = integers;
        }

        @Override
        protected void compute() {
            if (integers.size() <= 10) {
                // execute
                doExecute();
            } else {
                // fork
                forkCompute();
            }
        }

        // forking & create task
        private void forkCompute() {
            List<Integer> integers1 = this.integers.subList(0, this.integers.size() / 2);
            List<Integer> integers2 = this.integers.subList(this.integers.size() / 2, this.integers.size());

            SimpleForkJoinTask task1 = new SimpleForkJoinTask(integers1);
            SimpleForkJoinTask task2 = new SimpleForkJoinTask(integers2);

            ForkJoinTask.invokeAll(task1, task2);
        }

        private void doExecute() {
            integers.forEach(integer -> System.out.println(Thread.currentThread().getName() + " : " + integer));
        }
    }
}
