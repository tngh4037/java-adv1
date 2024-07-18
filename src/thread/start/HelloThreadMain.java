package thread.start;

public class HelloThreadMain {

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + ": main() start");

        HelloThread helloThread = new HelloThread();
        System.out.println(Thread.currentThread().getName() + ": start() 호출 전");
        helloThread.start();
        System.out.println(Thread.currentThread().getName() + ": start() 호출 후");

        System.out.println(Thread.currentThread().getName() + ": main() end");
    }
}

// 주의) run() 메서드가 아니라 반드시 start() 메서드를 호출해야 한다. 그래야 별도의 스레드에서 run() 코드가 실행된다.
// 스레드의 start() 메서드는 스레드에 스택 공간을 할당하면서 스레드를 시작하는 아주 특별한 메서드이다.
// 그리고 해당 스레드에서 run() 메서드를 실행한다.
// 따라서 main 스레드가 아닌, 별도의 스레드에서, 재정의한 run() 메서드를 실행하려면, 반드시 start() 메서드를 호출해야 한다.