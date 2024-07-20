package thread.control.interrupt;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class ThreadStopMainV3 {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        Thread thread = new Thread(task, "work");
        thread.start();

        sleep(100);
        log("작업 중단 지시 thread.interrupt()");
        thread.interrupt();
        log("work 스레드 인터럽트 상태1 = " + thread.isInterrupted()); // true
    }

    static class MyTask implements Runnable {

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) { // 인터럽트 상태 확인. (변경X)
                log("작업 중");
            }
            log("work 스레드 인터럽트 상태2 = " + Thread.currentThread().isInterrupted()); // true

            try {
                log("자원 정리");
                Thread.sleep(1000); // issue: 뜬금없이 여기서 인터럽트가 발생한다.
                log("자원 종료");
            } catch (InterruptedException e) {
                log("자원 정리 실패 - 자원 정리 중 인터럽트 발생");
                log("work 스레드 인터럽트 상태3 = " + Thread.currentThread().isInterrupted()); // false
            }

            log("작업 종료");
        }
    }
}

// [문제점]
// while 문을 빠져나왔는데 인터럽트 상태는 여전히 true 가 된다. ( InterruptedException 예외가 터질때야 인터럽트 상태가 바뀐다. (by 자바) )
// 그래서 이후에 로직에서 sleep() 과 같은 코드를 수행한다면, 의도치 않았던 해당 코드에서 인터럽트 예외가 발생하게 된다.
//
// 그럼 우리는 어떻게 해야할까? ( ThreadStopMainV4 )
// while(인터럽트_상태_확인) 같은 곳에서 인터럽트의 상태를 확인한 다음에, 만약 인터럽트 상태( true )라면 인터럽트 상태를 다시 정상( false )으로 돌려두면 된다.
