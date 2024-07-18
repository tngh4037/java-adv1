package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV2 {

    public static void main(String[] args) {
        log("main() start");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log("run()");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        log("main() end");
    }
}

// 특정 메서드 안에서만 간단히 정의하고 사용하고 싶다면, 익명 클래스를 사용하면 된다.
