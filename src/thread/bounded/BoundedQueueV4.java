package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;

public class BoundedQueueV4 implements BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition(); // 스레드 대기 집합

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV4(int max) {
        this.max = max;
    }

    @Override
    public /* synchronized */ void put(String data) {
        lock.lock();

        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기");
                try {
                    // wait();
                    condition.await(); // condition 안에서 대기한다.
                    log("[put] 생산자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.offer(data);
            log("[put] 생산자 데이터 저장, notify() 호출");
            // notify(); // condition 안에서 대기하던 쓰레드를 깨운다.
            condition.signal();
        } finally {

            lock.unlock();
        }
    }

    @Override
    public /* synchronized */ String take() {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터가 없음, 소비자 대기");
                try {
                    // wait();
                    condition.await(); // condition 안에서 대기한다.
                    log("[take] 소비자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            String data = queue.poll();
            log("[take] 소비자 데이터 획득, notify() 호출");
            // notify();
            condition.signal(); // condition 안에서 대기하던 쓰레드를 깨운다.

            return data;
        } finally {

            lock.unlock();
        }
    }

    @Override
    public String toString() { // 참고) 원래라면 여기에도 synchronized 를 넣어주는게 맞지만, 에제에서는 제외했다.
        return queue.toString();
    }
}
