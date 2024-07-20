package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV1 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 runFlag=false");
        task.runFlag = false;
    }

    static class MyTask implements Runnable {

        volatile boolean runFlag = true;

        @Override
        public void run() {
            while (runFlag) {
                log("작업 중");
                sleep(3000); // 3초 정도 소요되는 작업이라고 가정
            }
            log("자원 정리");
            log("자원 종료");
        }
    }
}

// 특정 스레드의 작업을 중단하는 가장 쉬운 방법은 변수를 사용하는 것이다.
// 여기서는 runFlag 를 사용해서 work 스레드에 작업 중단을 지시할 수 있다. 참고) 참고로 volatile 키워드는 뒤에서 자세히 설명한다. 지금은 단순히 여러 스레드에서 공유하는 값에 사용하는 키워드라고 알아두자.
//
//
// [문제점]
// main 스레드가 runFlag 를 false 로 변경해도, work 스레드는 while(runFlag) 코드를 실행해야, runFlag 를 확인하고 작업을 중단할 수 있다.
// 어떻게 하면 sleep() 처럼 스레드가 대기하는 상태에서 스레드를 깨우고, 작업도 빨리 종료할 수 있을까? ( ThreadStopMainV2 )