package thread.sync;

public interface BankAccount {

    boolean withdraw(int amount); // 출금

    int getBalance(); // 잔액 확인
    
}
