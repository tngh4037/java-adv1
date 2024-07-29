package thread.executor.future;

import java.util.concurrent.*;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class FutureCancelMain {

    // private static boolean mayInterruptIfRunning = true; // Future 를 취소 상태로 변경한다. 이때 작업이 실행중이라면 Thread.interrupt() 를 호출해서 작업을 중단한다 이후 Future.get() 을 호출하면 CancellationException 런타임 예외가 발생한다.
    private static boolean mayInterruptIfRunning = false; //  Future 를 취소 상태로 변경한다. 단 이미 실행 중인 작업을 중단하지는 않는다. ( 인터럽트를 걸지 않는다. ) => 실행중인 작업은 그냥 두더라도 cancel() 을 호출했기 때문에 Future 는 CANCEL 상태가 된다. 따라서 이후 이후 Future.get() 을 호출하면 CancellationException 런타임 예외가 발생한다.

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(new MyTask());
        log("Future.state: " + future.state());
        
        // 일정 시간 후 취소 시도
        sleep(3000);

        // cancel() 호출
        log("future.cancel(" + mayInterruptIfRunning + ") 호출");
        boolean cancelResult = future.cancel(mayInterruptIfRunning);
        log("cancel(" + mayInterruptIfRunning + ") result: " + cancelResult);

        // 결과 확인
        try {
            log("Future result: " + future.get());
        } catch (CancellationException e) {
            log("Future는 이미 취소 되었습니다.");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        es.close();
    }

    static class MyTask implements Callable<String> {

        @Override
        public String call() {

            try {
                for (int i = 0; i < 10; i++) {
                    log("작업 중: " + i);
                    Thread.sleep(1000); // 1초 동안 sleep
                }
            } catch (InterruptedException e) {
                log("인터럽트 발생");
                return "Interrupted";
            }

            return "Completed";
        }
    }

}
