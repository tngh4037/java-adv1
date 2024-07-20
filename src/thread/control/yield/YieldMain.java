package thread.control.yield;

import static util.ThreadUtils.sleep;

public class YieldMain {

    static final int THREAD_COUNT = 1000;

    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            // 1) empty : 하나의 스레드가 연속적으로 실행되는 경우가 많다.
            // for (int i = 0; i < 10; i++) {
            //     System.out.println(Thread.currentThread().getName() + " - " + i);
            // }

            // 2) sleep(1) : 하나의 스레드가 연속적으로 실행되지 않고, 거의 계속 바뀐다.
            // for (int i = 0; i < 10; i++) {
            //     System.out.println(Thread.currentThread().getName() + " - " + i);
            //     sleep(1);
            // }

            // 3) Thread.yield() : yield는 스레드의 상태가 바뀌는게 아니다. ( RUNNABLE 상태를 유지하는데, CPU 실행에서 빠져서 스케줄링에 다시 들어가는 것. )
            // 실행해보면 sleep 과 비슷하게 거의 계속 바뀌긴 하지만, sleep 보다는 그래도 하나의 스레드가 좀 더 자주 실행된다. ( 1)과 2)의 중간느낌 )
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
                Thread.yield();
            }
        }
    }

}
