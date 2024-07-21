package thread.volatile1;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class VolatileFlagMain {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread t = new Thread(task, "work");
        log("runFlag = " + task.runFlag);
        t.start();

        sleep(1000);

        log("runFlag를 false로 변경 시도");
        task.runFlag = false;
        log("runFlag = " + task.runFlag);
        log("main 종료");
    }

    static class MyTask implements Runnable {

        boolean runFlag = true;
        // volatile boolean runFlag = true;

        @Override
        public void run() {
            log("task 시작");
            while (runFlag) {
                // runFlag 가 false 로 변하면 탈출
            }
            log("task 종료");
        }
    }
}

// 위 코드를 보면, main 스레드는 sleep() 을 통해 1초간 쉰 다음에 runFlag 를 false 로 설정한다.
// 그러면, runFlag 가 false가 되므로 work 스레드는 작업을 종료해야 한다.
// 그런데 실제 실행 결과는 기대하는 실행 결과와 다르게, work 스레드가 while문에서 빠져나오지 못하고 있다. ( 자바 프로그램이 멈추지 않고 계속 실행된다. )
// 도대체 어떻게 된 일일까? -> 메모리 가시성 문제
//
// 해결방안) volatile : 캐시 메모리를 사용하는 성능 상의 이점을 약간 포기하는 대신에, 값을 읽을 때, 값을 쓸 때 모두 메인 메모리에 직접 접근하도록 하는 기능 제공.
// ㄴ 이렇게 하면 runFlag 에 대해서는 캐시 메모리를 사용하지 않고, 값을 읽거나 쓸 때 항상 메인 메모리에 직접 접근한다.
//
//
// 여러 스레드에서 같은 값을 읽고 써야 한다면 volatile 키워드를 사용하면 된다.
// 단, 캐시 메모리를 사용할 때 보다 성능이 느려지는 단점이 있기 때문에 꼭! 필요한 곳에만 사용하는 것이 좋다.
