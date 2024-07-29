package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import static util.MyLogger.log;

// 스레드 풀에 현재 스레드가 몇 개있고, 스레드의 상태는 어떻고 등등 이런 것들을 확인하기 위한 유틸
public abstract class ExecutorUtils {

    public static void printState(ExecutorService executorService) {
        if (executorService instanceof ThreadPoolExecutor poolExecutor) {
            int pool = poolExecutor.getPoolSize(); // 스레드 풀에 있는 스레드 갯수 ( 스레드 풀에서 관리되는 스레드의 숫자 )
            int active = poolExecutor.getActiveCount(); // 실제 작업 중인 스레드 갯수
            int queuedTasks = poolExecutor.getQueue().size();// 작업이 큐에 몇개 들어가 있는지 갯수 ( 큐에 대기중인 작업의 숫자 )
            long completedTaskCount = poolExecutor.getCompletedTaskCount(); // 완료된 작업 갯수
            log("[pool=" + pool + ", active=" + active +
                    ", queuedTasks=" + queuedTasks + ", completedTaskCount=" + completedTaskCount + "]");
        } else {
            log(executorService);
        }
    }

}
