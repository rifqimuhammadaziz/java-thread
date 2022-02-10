package rifqimuhammadaziz.concurrency;

public class Balance {

    private Long value;

    public Balance(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    // deadlock method (from & to are waiting done for each other)
    public static void transferDeadlock(Balance from, Balance to, Long value) throws InterruptedException {
        // lock from
        synchronized (from) {
            Thread.sleep(1_000);
            // lock to
            synchronized (to) {
                Thread.sleep(1_000);
                from.setValue(from.getValue() - value);
                to.setValue(to.getValue() + value);
            }
        }
    }

    // handle deadlock (create manual solution to handle deadlock)
    public static void transferCorrect(Balance from, Balance to, Long value) throws InterruptedException {
        // lock & change balance 'from' then unlock
        synchronized (from) {
            Thread.sleep(1_000);
            from.setValue(from.getValue() - value);
        }

        // lock & change balance 'to' then unlock
        synchronized (to) {
            Thread.sleep(1_000);
            to.setValue(to.getValue() + value);
        }

    }

}
