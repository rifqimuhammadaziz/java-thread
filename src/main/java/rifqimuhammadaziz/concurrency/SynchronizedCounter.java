package rifqimuhammadaziz.concurrency;

public class SynchronizedCounter {

    private Long value = 0L;

    // handle race condition
    // this method only accessible 1 thread in 1 time (synchronized)
    public synchronized void increment() {
        value++;
    }

    public void increment2() {
        // this block code not use synchronized

        // this block only accessible in 1 thread in 1 time
        synchronized (this) {
            value++;
        }

        // this block code not use synchronized
    }

    public Long getValue() {
        return value;
    }
}
