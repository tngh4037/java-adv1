package thread.cas.increment;

import java.util.ArrayList;

import static util.ThreadUtils.sleep;

public class IncrementThreadMain {

    public static final int THREAD_COUNT = 1000;

    public static void main(String[] args) throws InterruptedException {
        test(new BasicInteger());
        test(new VolatileInteger());
        test(new SyncInteger());
    }

    private static void test(IncrementInteger incrementInteger) throws InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sleep(10); // 참고) 스레드가 너무 빨리 실행되기 때문에, 여러 스레드가 동시에 실행되는 상황을 확인하기 어렵다. 그래서 run() 메서드에 sleep(10) 을 두어서, 최대한 많은 스레드가 동시에 increment() 를 호출하도록 한다. ( 물론 실행 환경에 따라서 1000이 보일 수도 있다. )
                incrementInteger.increment();
            }
        };

        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);

            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        int result = incrementInteger.get();
        System.out.println(incrementInteger.getClass().getSimpleName() + " result: " + result);
    }
}
