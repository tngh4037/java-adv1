package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV4 {

    public static void main(String[] args) throws InterruptedException {
        log("Start");

        SumTask task1 = new SumTask(1, 50);
        Thread thread1 = new Thread(task1, "thread-1");

        thread1.start();

        // (추가) join(ms) - 대상 스레드를 특정 시간 만큼만 대기한다.
        log("join(1000) - main 스레드가 thread1 종료까지 1초 대기");
        thread1.join(1000); // thread1 이 종료될 때 까지 main 스레드는 1초까지만 기다린다. 참고로 이때 main 스레드의 상태는 WAITING 이 아니라 TIMED_WAITING 이 된다.
        log("join() - main 스레드 대기 완료");
        
        log("task1.result = " + task1.result);

        log("End");
    }

    static class SumTask implements Runnable {
        int startValue;
        int endValue;
        int result = 0;

        public SumTask(int startValue, int endValue) {
            this.startValue = startValue;
            this.endValue = endValue;
        }

        @Override
        public void run() {
            log("작업 시작");
            sleep(2000); // 수행하는 계산이 2초 정도는 걸리는 복잡한 계산이라고 가정
            int sum = 0;
            for (int i = startValue; i <= endValue; i++) {
                sum += i;
            }
            result = sum;
            log("작업 완료 result = " + result);
        }
    }
}
