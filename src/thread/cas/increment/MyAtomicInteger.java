package thread.cas.increment;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAtomicInteger implements IncrementInteger {

    AtomicInteger value = new AtomicInteger(0); // 참고) 생성자에 초기값을 지정한다. 생략하면 0 부터 시작한다.

    @Override
    public void increment() {
        value.incrementAndGet();
    }

    @Override
    public int get() {
        return value.get();
    }
}
