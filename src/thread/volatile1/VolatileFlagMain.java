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
