package thread.executor.future;

import java.util.Random;
import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class CallableMainV1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(1); // 참고) Executors : ThreadPoolExecutor 생성을 편하게 할 수있는 유틸
        Future<Integer> future = es.submit(new MyCallable());
        Integer result = future.get();
        log("result value = " + result);
        es.close();
    }

    static class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() /* throws Exception */ {
            log("Callable 시작");
            sleep(2000); // 작업에 대략 2초 정도 소요된다고 가정
            int value = new Random().nextInt(10); // 0 ~ 9 사이 리턴
            log("create value = " + value);
            log("Callable 완료");
            return value;
        }
    }
}
