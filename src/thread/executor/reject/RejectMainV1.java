package thread.executor.reject;

import thread.executor.RunnableTask;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

// AbortPolicy
public class RejectMainV1 {

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1,
                0, TimeUnit.SECONDS, new SynchronousQueue<>(), new ThreadPoolExecutor.AbortPolicy());

        executor.submit(new RunnableTask("task1"));

        try {
            executor.submit(new RunnableTask("task2")); // java.util.concurrent.RejectedExecutionException
        } catch (RejectedExecutionException e) {
            log("요청 초과");
            // 포기, 다시 시도 등 다양한 고민을 하면 됨
            log(e);
        }

        executor.close();
    }
}

// ThreadPoolExecutor 는 거절해야 하는 상황이 발생하면 RejectedExecutionHandler 구현체의 rejectedExecution() 을 호출한다.