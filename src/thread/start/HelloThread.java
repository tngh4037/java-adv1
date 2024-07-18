package thread.start;

public class HelloThread extends Thread { // Thread 클래스를 상속한다.

    @Override
    public void run() { // 스레드가 실행할 코드를 run() 메서드에 재정의한다.
        System.out.println(Thread.currentThread().getName() + ": run()");
    }
}

// 참고)
// - Thread.currentThread() 를 호출하면 해당 코드를 실행하는 스레드 객체를 조회할 수 있다.
// - Thread.currentThread().getName(): 현재 실행 중인 스레드의 이름을 조회한다.