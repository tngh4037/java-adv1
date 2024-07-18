package thread.start;

public class DaemonThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        DaemonThread daemonThread = new DaemonThread();
        daemonThread.setDaemon(true); // 데몬 스레드 여부 (default: false(사용자 스레드)) | 참고) 데몬 스레드 여부는 start() 실행 전에 결정해야 한다. 이후에는 변경되지 않는다.
        daemonThread.start();
        
        System.out.println(Thread.currentThread().getName() + ": main() end");
    }

    static class DaemonThread extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": run() start");

            try {
                Thread.sleep(10000); // 가정) 뭔가 로직이 10초간 실행되는 중
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println(Thread.currentThread().getName() + ": run() end");
        }
    }
}

// Thread-0: run() end 가 출력되기 전에 프로그램이 종료된다. 10초 동안 기다리지 않는 것이다. ( 유일한 user 스레드인 main 스레드가 종료되면서 자바 프로그램도 종료된다. )
// 참고) 만약 daemonThread.setDaemon(false) 로 하면(=사용자 스레드), 10초동안 대기하는 것을 확인할 수 있다.
