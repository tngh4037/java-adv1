package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV2 {

    public static void main(String[] args) {
        log("Start");

        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        // (추가) sleep
        log("main 스레드 sleep()");
        sleep(3000);
        log("main 스레드 wake up");

        log("task1.result = " + task1.result);
        log("task2.result = " + task2.result);

        int sumAll = task1.result + task2.result;
        log("task1 + task2 = " + sumAll);

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

// 특정 스레드를 기다리게 하는 가장 간단한 방법은 sleep()을 사용하는 것이다. 그래서 원하는 결과를 얻었다.
// 그런데 문제가 있다. 정확한 타이밍을 맞추어 기다리기 어렵다는 것이다. (thread-1 과 thread-2 가 언제 끝날지 알수없다.)
//
// 따라서, 더 나은 방법은 thread-1 , thread-2 가 계산을 끝내고 종료될 때 까지 main 스레드가 기다리는 방법이다.
// ㄴ 이때 join() 메서드를 사용하면 깔끔하게 문제를 해결할 수 있다. ( JoinMainV3 )