package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;

public class SumTaskMainV2_Bad {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);

        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Integer> future1 = es.submit(task1); // non-blocking
        Integer sum1 = future1.get(); // blocking, 2초 대기

        Future<Integer> future2 = es.submit(task2); // non-blocking
        Integer sum2 = future2.get(); // blocking, 2초 대기

        log("task1.result = " + sum1);
        log("task2.result = " + sum2);

        int sumAll = sum1 + sum2;
        log("task1 + task2 = " + sumAll);
        log("End");

        es.close();
    }

    static class SumTask implements Callable<Integer> {

        int startValue;
        int endValue;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public Integer call() throws InterruptedException {
            log("작업 시작");

            Thread.sleep(2000);

            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }

            log("작업 완료 result=" + sum);
            return sum;
        }
    }

}

// [ Future를 잘못 활용한 예 ]
// 요청 스레드가 작업을 하나 요청하고 그 결과를 기다린다. 그리고 그 다음에 다시 다음 요청을 전달하고 결과를 기다린다.
