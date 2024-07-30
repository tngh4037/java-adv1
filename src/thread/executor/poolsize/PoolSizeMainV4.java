package thread.executor.poolsize;

import thread.executor.ExecutorUtils;
import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

// Executor 전략 - 사용자 정의 풀 전략
public class PoolSizeMainV4 {

    static final int TASK_SIZE = 1100; // 1. 일반
    // static final int TASK_SIZE = 1200; // 2. 긴급
    // static final int TASK_SIZE = 1201; // 3. 거절

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(100, 200,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));
        ExecutorUtils.printState(es);

        long startMs = System.currentTimeMillis();
        for (int i = 1; i <= TASK_SIZE; i++) {
            String taskName = "task" + i;
            try {
                es.execute(new RunnableTask(taskName));
                ExecutorUtils.printState(es, taskName);
            } catch (RejectedExecutionException e) {
                log(taskName + " -> " + e);
            }
        }
        
        es.close();
        long endMs = System.currentTimeMillis();
        log("time: " + (endMs - startMs));
    }
}
