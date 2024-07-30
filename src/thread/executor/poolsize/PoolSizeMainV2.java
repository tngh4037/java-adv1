package thread.executor.poolsize;

import thread.executor.ExecutorUtils;
import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static util.MyLogger.log;

// Executor 전략 - 고정 풀 전략
public class PoolSizeMainV2 {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2); // ExecutorService es = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        log("pool 생성");
        ExecutorUtils.printState(es);

        for (int i = 1; i <= 6; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            ExecutorUtils.printState(es, taskName);
        }

        es.close();
        log("== shutdown 완료 ==");
    }
}
