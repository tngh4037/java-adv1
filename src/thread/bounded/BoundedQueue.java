package thread.bounded;

public interface BoundedQueue { // 버퍼 역할을 하는 큐의 인터페이스

    void put(String data); // 버퍼에 데이터를 보관한다. (생산자 스레드가 호출)
    
    String take(); // 버퍼에 보관된 값을 가져간다. (소비자 스레드가 호출)

}
