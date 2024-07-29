package thread.executor.future;

import thread.executor.CallableTask;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static util.MyLogger.log;

public class InvokeAnyMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newFixedThreadPool(10);

        CallableTask task1 = new CallableTask("task1", 1000);
        CallableTask task2 = new CallableTask("task2", 2000);
        CallableTask task3 = new CallableTask("task3", 3000);

        List<CallableTask> tasks = List.of(task1, task2, task3);

        Integer value = es.invokeAny(tasks); // invokeAny 는 한 번에 여러 작업을 제출하고, 가장 먼저 완료된 작업의 결과를 반환한다. 이때 완료되지 않은 나머지 작업은 인터럽트를 통해 취소한다.
        log("value = " + value);

        es.close();
    }
}
