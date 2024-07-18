package thread.start;

import static util.MyLogger.log;

public class InnerRunnableMainV3 {

    public static void main(String[] args) {
        log("main() start");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                log("run()");
            }
        });

        thread.start();

        log("main() end");
    }
}

// V2 개선 ( 익명 클래스를 참조하는 변수를 만들지 않고 직접 전달할 수 있다. )
