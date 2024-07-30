package thread.executor.poolsize;

import thread.executor.ExecutorUtils;
import thread.executor.RunnableTask;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

// Executor 전략 - 캐시 풀 전략
public class PoolSizeMainV3 {

    public static void main(String[] args) {
        // ExecutorService es = Executors.newCachedThreadPool(); // ExecutorService es = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
        ExecutorService es = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3, TimeUnit.SECONDS, new SynchronousQueue<Runnable>()); // 예제 확인을 위해 keepAliveTime(초과스레드 대기 시간)을 3으로 조정하였다.
        log("pool 생성");
        ExecutorUtils.printState(es);

        for (int i = 1; i <= 4; i++) {
            String taskName = "task" + i;
            es.execute(new RunnableTask(taskName));
            ExecutorUtils.printState(es, taskName);
        }

        sleep(3000);
        log("== 작업 수행 완료 ==");
        ExecutorUtils.printState(es); // [pool=4, active=0, queuedTasks=0, completedTaskCount=4]

        sleep(3000);
        log("== maximumPoolSize 대기 시간 초과 ==");
        ExecutorUtils.printState(es); // [pool=0, active=0, queuedTasks=0, completedTaskCount=4]

        es.close();
        log("== shutdown 완료 ==");
        ExecutorUtils.printState(es);
    }
}
