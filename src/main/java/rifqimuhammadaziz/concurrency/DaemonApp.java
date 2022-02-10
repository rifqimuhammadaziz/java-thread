package rifqimuhammadaziz.concurrency;

public class DaemonApp {

    public static void main(String[] args) {
        var thread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("Run Thread");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.setDaemon(true); // kill unused thread (not waiting program done)
        thread.start();
    }
}
