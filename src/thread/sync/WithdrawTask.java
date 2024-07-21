package thread.sync;

public class WithdrawTask implements Runnable {

    private BankAccount account;
    private int amount; // 출금액

    public WithdrawTask(BankAccount account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        account.withdraw(amount);
    }
}
