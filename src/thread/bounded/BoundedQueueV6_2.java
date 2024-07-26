package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static util.MyLogger.log;

// BlockingQueue - Special Value ( offer / poll )
public class BoundedQueueV6_2 implements BoundedQueue {

    protected BlockingQueue<String> queue;

    public BoundedQueueV6_2(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        boolean result = queue.offer(data);
        log("저장 시도 결과 = " + result);
    }

    @Override
    public String take() {
        return queue.poll();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}

// 참고) 실행 결과를 보면 뭔가 우리가 처음 작성한 BoundedQueueV1 과 비슷하다.