package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileCountMain {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        t.start();

        sleep(1000);

        task.flag = false;
        log("flag = " + task.flag + ", count = " + task.count + " in main");
    }

    static class MyTask implements Runnable {

        // boolean flag = true;
        // long count;

         volatile boolean flag = true;
         volatile long count;

        @Override
        public void run() {

            while (flag) {
                count++;
                // 1억번에 한번씩 출력
                if (count % 100_000_000 == 0) {
                    log("flag = " + flag + ", count = " + count + " in while()");
                }
            }
            log("flag = " + flag + ", count = " + count + " 종료");
        }

    }
}

// [실행결과 참고]
// [     main] flag = false, count = 199886574 in main
// [     work] flag = true, count = 200000000 in while()
// [     work] flag = false, count = 200000000 종료
//
// main 에서 false로 변경한 시점에 출력된 count 는 199886574 인데, 실제 work 스레드에서는 count 가 200000000 이 된 시점에야 false 를 확인하고 종료되었다.
// main 에서 false로 변경한 시점과, work 스레드가 false 를 확인한 시점이 다른 것이다.
// 결과적으로 main 스레드가 flag 값을 false 로 변경하고 한참이 지나서야 work 스레드는 flag 값이 false 로 변경된 것을 인지한 것이다. (메모리 가시성 문제)
//
// 결국 이 상황에서 메모리 가시성 문제를 확실하게 해결하려면 volatile 키워드를 사용해야 한다.
//
//
// [volatile 적용 후 실행결과 참고]
// [     work] flag = false, count = 83861262 종료
// [     main] flag = false, count = 83861262 in main
//
// main 스레드가 flag 를 변경하는 시점에 work 스레드도 flag 의 변경 값을 정확하게 확인할 수 있다.
// 참고) volatile 을 적용하면 캐시 메모리가 아니라 메인 메모리에 항상 직접 접근하기 때문에 성능이 상대적으로 떨어진다.