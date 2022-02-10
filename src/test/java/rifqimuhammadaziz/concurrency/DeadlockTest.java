package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

public class DeadlockTest {

    @Test
    void transferDeadlock() throws InterruptedException {
        var balance1 = new Balance(1_000_000L);
        var balance2 = new Balance(1_000_000L);

        var thread1 = new Thread(() -> {
            try {
                Balance.transferDeadlock(balance1, balance2, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                Balance.transferDeadlock(balance2, balance1, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join(); // wait until done
        thread2.join(); // wait until done

        System.out.println("Balance 1 : " + balance1.getValue());
        System.out.println("Balance 2 : " + balance2.getValue());
    }

    @Test
    void transferCorrect() throws InterruptedException {
        var balance1 = new Balance(1_000_000L);
        var balance2 = new Balance(1_000_000L);

        var thread1 = new Thread(() -> {
            try {
                Balance.transferCorrect(balance1, balance2, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                Balance.transferCorrect(balance2, balance1, 500_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join(); // wait until done
        thread2.join(); // wait until done

        System.out.println("Balance 1 : " + balance1.getValue());
        System.out.println("Balance 2 : " + balance2.getValue());
    }
}
