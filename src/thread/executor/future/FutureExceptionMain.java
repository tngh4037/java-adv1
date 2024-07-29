package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureExceptionMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        log("작업 전달");
        Future<Integer> future = es.submit(new ExCallable());

        sleep(1000); // 잠시 대기

        try {
            log("future.get() 호출 시도, future.state(): " + future.state()); // 예외가 발생하면 "FAILED" 상태가 된다.
            Integer result = future.get();
            log("result value = " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) { // call() 실행 중에 예외가 발생했다면, future.get() 조회시 ExecutionException 로 터진다.
            log("e = " + e);
            Throwable cause = e.getCause(); // ExecutionException 가 발생하게 된 원본 예외 조회
            log("cause = " + cause); // java.lang.IllegalStateException: ex!
        }

        es.close();
    }

    static class ExCallable implements Callable<Integer> {

        @Override
        public Integer call() /* throws Exception */ {
            log("Callable 실행, 예외 발생");
            throw new IllegalStateException("ex!");
        }
    }

}
