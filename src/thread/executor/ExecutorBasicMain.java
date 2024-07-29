package thread.executor;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ExecutorBasicMain {

    public static void main(String[] args) {
        ExecutorService es = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

        log("== 초기 상태 ==");
        ExecutorUtils.printState(es); // 참고) 초기 상태를 로그로 보면, [pool=0, active=0, queuedTasks=0, completedTaskCount=0] 이다. 왜냐하면 작업이 하나라도 와야 스레드를 만든다. (작업이 올지 안올지 모르기 때문에 최초에는 안 만들고, 작업이 오면 그때 만든다.)

        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("taskD"));

        log("== 작업 수행 중 ==");
        ExecutorUtils.printState(es); // [pool=2, active=2, queuedTasks=2, completedTaskCount=0]

        sleep(3000);

        log("== 작업 수행 완료 ==");
        ExecutorUtils.printState(es); // [pool=2, active=0, queuedTasks=0, completedTaskCount=4]

        es.close(); // 스레드 풀 종료 ( 풀에 있는 스레드가 모두 제거된다. )
        log("== shutdown 완료 ==");
        ExecutorUtils.printState(es); // [pool=0, active=0, queuedTasks=0, completedTaskCount=4]
    }
}
