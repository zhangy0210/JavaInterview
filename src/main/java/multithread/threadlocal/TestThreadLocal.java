package multithread.threadlocal;


/**
 * ThreadLocal使得每个线程拥有自己的局部变量
 *
 *
 */

class MyThreadLocal{
    static private ThreadLocal<String> myThreadLocal = new ThreadLocal<>();
    public void setSomething() {
        myThreadLocal.set("MyThreadLocal");
    }
    public String getSomething() {
        return myThreadLocal.get();
    }
}

public class TestThreadLocal {
    public static void main(String[] args) {
        new Thread(() -> {
            MyThreadLocal myThreadLocal = new MyThreadLocal();
            myThreadLocal.setSomething();
            System.out.println("Thread a get" + myThreadLocal.getSomething());
        }).start();
        new Thread(() -> {
            new MyThreadLocal().setSomething();
            System.out.println("Thread c get" + new MyThreadLocal().getSomething());
        }).start();
        new Thread(() -> {
            System.out.println("Thread b get" + new MyThreadLocal().getSomething());
        }).start();
    }
}


