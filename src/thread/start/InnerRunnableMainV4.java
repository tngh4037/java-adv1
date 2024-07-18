package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV4 {

    public static void main(String[] args) {
        log("main() start");

        Thread thread = new Thread(() -> log("run()"));
        thread.start();

        log("main() end");
    }
}

// V3 개선 (lambda)
// - 참고) 람다를 사용하면 메서드(함수) 코드 조각을 전달할 수 있다.