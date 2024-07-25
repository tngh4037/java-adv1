package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;

// 하나의 락에 대해, 대기 공간 분리
public class BoundedQueueV5 implements BoundedQueue {

    private final Lock lock = new ReentrantLock();
    private final Condition producerCond = lock.newCondition(); // 생산자 스레드 대기 공간
    private final Condition consumerCond = lock.newCondition(); // 소비자 스레드 대기 공간

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV5(int max) {
        this.max = max;
    }

    @Override
    public void put(String data) {
        lock.lock();

        try {
            while (queue.size() == max) {
                log("[put] 큐가 가득 참, 생산자 대기");
                try {
                    producerCond.await(); // condition(생산자 용) 안에서 대기한다.
                    log("[put] 생산자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            queue.offer(data);
            log("[put] 생산자 데이터 저장, consumerCond.signal() 호출");
            consumerCond.signal(); // condition(소비자 용) 안에서 대기하던 쓰레드를 깨운다.
        } finally {

            lock.unlock();
        }
    }

    @Override
    public String take() {
        lock.lock();

        try {
            while (queue.isEmpty()) {
                log("[take] 큐에 데이터가 없음, 소비자 대기");
                try {
                    consumerCond.await(); // condition(소비자 용) 안에서 대기한다.
                    log("[take] 소비자 깨어남");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            String data = queue.poll();
            log("[take] 소비자 데이터 획득, consumerCond.signal() 호출");
            producerCond.signal(); // condition(생산자 용) 안에서 대기하던 쓰레드를 깨운다.

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

// 생산자가 생산자를 깨우고, 소비자가 소비자를 깨우는 비효율 문제 해결.