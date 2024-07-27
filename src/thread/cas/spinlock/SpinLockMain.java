package thread.cas.spinlock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class SpinLockMain {

    public static void main(String[] args) {
        // SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                spinLock.lock();

                try {
                    // critical section
                    log("비즈니스 로직 실행");
                    // sleep(10); // 참고) 오래 걸리는 로직에서는 스핀 락 사용하면 안된다. ( 락을 기다리는 스레드가 CPU를 계속 사용하면서 반복문 계속 탐. )
                } finally {
                    spinLock.unlock();
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
