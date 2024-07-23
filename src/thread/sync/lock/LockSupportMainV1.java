package thread.sync.lock;

import java.util.concurrent.locks.LockSupport;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class LockSupportMainV1 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new ParkTest(), "Thread-1");
        thread1.start();

        sleep(100); // 참고) 잠시 대기하여 Thread-1 이 park 상태에 빠질 시간을 준다.
        log("Thread-1 state: " + thread1.getState());

        log("main -> unpark(Thread-1)");
        LockSupport.unpark(thread1); // 1-1) unpark(깨울 스레드) 사용 | WAITING -> RUNNABLE
        // thread1.interrupt(); // 1-2) interrupt() 사용 | WAITING 상태의 스레드에 인터럽트가 발생하면 WAITING 상태에서 RUNNABLE 상태로 변하면서 깨어난다.

    }

    static class ParkTest implements Runnable {

        @Override
        public void run() {
            log("park 시작");
            LockSupport.park(); // RUNNABLE -> WAITING
            log("park 종료, state: " + Thread.currentThread().getState());
            log("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        }
    }
}
