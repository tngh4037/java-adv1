package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV2 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(4000);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted()); // true
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    log("작업 중");
                    Thread.sleep(3000); // 3초 정도 소요되는 작업이라고 가정
                }
            } catch (InterruptedException e) {
                log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // false
                log("interrupt message = " + e.getMessage());
                log("state = " + Thread.currentThread().getState());
            }
            log("자원 정리");
            log("자원 종료");
        }
    }
}

// 인터럽트를 사용하면 대기중인 스레드에 인터럽트 예외를 발생시켜, (깨워서) 실행 가능한 상태로 바꿀 수 있다.
// 인터럽트가 적용되고, 인터럽트 예외가 발생하면, 해당 스레드는 실행 가능 상태가 되고, 인터럽트 발생 상태도 정상으로 돌아온다.(false)
//
// 참고) interrupt() 를 호출했다고 해서 즉각 InterruptedException 이 발생하는 것은 아니다. 오직 sleep() 처럼 InterruptedException 을 던지는 메서드를 호출 하거나 또는 호출 중일 때 예외가 발생한다.
