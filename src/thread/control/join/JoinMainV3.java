package thread.control.join;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class JoinMainV3 {

    public static void main(String[] args) throws InterruptedException {
        log("Start");

        SumTask task1 = new SumTask(1, 50);
        SumTask task2 = new SumTask(51, 100);
        Thread thread1 = new Thread(task1, "thread-1");
        Thread thread2 = new Thread(task2, "thread-2");

        thread1.start();
        thread2.start();

        // (추가) join - 스레드가 종료될 때 까지 대기
        log("join() - main 스레드가 thread1, thread2 종료까지 대기");
        thread1.join(); // thread1 이 종료될 때 까지 main 스레드는 기다린다. (WAITING)
        thread2.join(); // thread2 이 종료될 때 까지 main 스레드는 기다린다. (WAITING)
        log("join() - main 스레드 대기 완료");
        
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

// main 스레드는 thread-1 , thread-2 가 종료될 때 까지 기다린다. 참고로 이때 main 스레드는 WAITING 상태가 된다.
// 이렇듯 특정 스레드가 완료될 때 까지 기다려야 하는 상황이라면 join() 을 사용하면 된다.
//
// 하지만 join() 의 단점은 다른 스레드가 완료될 때 까지 "무기한" 기다리는 단점이 있다.
// 비유를 하자면 맛집에 한 번 줄을 서면 중간에 포기하지 못하고 자리가 날 때 까지 무기한 기다려야 한다.
// 만약 다른 스레드의 작업을 일정 시간 동안만 기다리고 싶다면 어떻게 해야할까? ( JoinMainV4 )
