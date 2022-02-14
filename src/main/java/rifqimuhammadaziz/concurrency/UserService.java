package rifqimuhammadaziz.concurrency;

public class UserService {

    // save data in one thread (handle race condition)
    final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    // race condition
    // private String user;

    public void setUser(String user) {
        threadLocal.set(user);

        // race condition
        // this.user = user;
    }

    public void doAction() {
        String user = threadLocal.get();
        System.out.println(user + " do Action.");
    }
}
