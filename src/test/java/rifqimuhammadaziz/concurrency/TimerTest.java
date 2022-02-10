package rifqimuhammadaziz.concurrency;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void delayedJob() throws InterruptedException {
        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        var timer = new Timer();
        timer.schedule(task, 2000); // sleep 2s then run task

        Thread.sleep(3000L);
    }

    @Test
    void periodicJob() throws InterruptedException {
        var task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Delayed Job");
            }
        };

        var timer = new Timer();
        timer.schedule(task, 2000, 2000); // sleep 2s then run task every 2s

        Thread.sleep(10000L);
    }
}
