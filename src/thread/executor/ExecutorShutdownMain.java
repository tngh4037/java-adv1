package thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.MyLogger.log;

public class ExecutorShutdownMain {

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(2);
        es.execute(new RunnableTask("taskA"));
        es.execute(new RunnableTask("taskB"));
        es.execute(new RunnableTask("taskC"));
        es.execute(new RunnableTask("longTask", 100_000)); // 100초 동안 수행한다고 가정
        ExecutorUtils.printState(es);

        log("== shutdown 시작");
        shutdownAndAwaitTermination(es);
        log("== shutdown 완료");
        ExecutorUtils.printState(es);
    }

    private static void shutdownAndAwaitTermination(ExecutorService es) {
        es.shutdown(); // 1) 새로운 작업을 받지 않는다. 2) 처리중이거나 큐에 이미 대기중인 작업은 처리한다. 3) 이후에 풀의 스레드를 종료한다. | 참고) non-blocking (shutdown() 을 호출한 스레드는 다 마무리될때까지 대기하지 않는다. 계속 다음 로직들 실행)

        try {
            // 대기중인 작업들을 모두 완료할 때 까지 10초 기다린다. ( 10초 안에 모든 작업이 완료된다면 true 를 반환한다. )
            if (!es.awaitTermination(10, TimeUnit.SECONDS)) {

                // 정상 종료가 너무 오래 걸리면..
                log("서비스 정상 종료 실패 -> 강제 종료 시도");
                es.shutdownNow();

                // 작업이 취소될 때 까지 대기한다. (강제 종료에도 어느정도 자원 정리 등에 시간이 걸리니까)
                if (!es.awaitTermination(10, TimeUnit.SECONDS)) {
                    log("서비스가 종료되지 않았습니다."); // 참고) 이건 문제가 있는 것이다. 개발자 noti 해줘야 함.
                }
            }
        } catch (InterruptedException e) {
            // awaitTermination() 으로 대기중인 현재 스레드가 인터럽트 될 수 있다.
            es.shutdownNow();
        }

    }
}
