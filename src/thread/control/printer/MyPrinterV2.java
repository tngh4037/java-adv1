package thread.control.printer;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class MyPrinterV2 {

    public static void main(String[] args) {
        Printer printer = new Printer();
        Thread printerThread = new Thread(printer, "printer");
        printerThread.start();

        Scanner userInput = new Scanner(System.in);
        while (true) {
            log("프린터할 문서를 입력하세요. 종료(q): ");
            String input = userInput.nextLine();
            if (input.equals("q")) {
                printer.work = false;
                printerThread.interrupt();
                break;
            }

            printer.addJob(input);
        }
    }

    static class Printer implements Runnable {

        volatile boolean work = true;
        Queue<String> jobQueue = new ConcurrentLinkedQueue<>();

        @Override
        public void run() {
             while (work) {
                if (jobQueue.isEmpty()) {
                    continue;
                }

                 try {
                     String job = jobQueue.poll();
                     log("출력 시작: " + job + ", 대기 문서: " + jobQueue);
                     Thread.sleep(3000); // 하나 출력시 3초 걸린다고 가정
                     log("출력 완료");
                 } catch (InterruptedException e) {
                     log("인터럽트!");
                     break;
                 }
            }

            log("프린터 종료");
        }

        public void addJob(String input) {
            jobQueue.offer(input);
        }
    }
}

// 종료시 main 스레드는 work 변수도 false 로 변경하고, printer 스레드에 인터럽트도 함께 호출한다.
// 이렇게 둘 다 함께 적용하면, printer 스레드가 sleep() 을 호출한 상태는 물론이고, while (work) 코드가 실행되는 부분에서도 빠져나올 수 있어서 반응성이 더 좋아진다.
//  ㄴ interrupt() : sleep() 상태에서 빠져나온다.
//  ㄴ work=false : while문을 체크하는 곳에서 빠져나온다.
//
// 그런데 위 코드에서, 인터럽트 예외가 발생하지 않고, work 에서만 걸린 경우라면, while 문을 빠져나왔음에도 그대로 인터럽트 상태가 된다. 따라서 이 부분도 개선되면 좋다.
