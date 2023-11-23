package multithreading;

public class UncaughtExceptionEx1 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName("Misbehaving thread");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
                System.out.println("A critical error happened in thread " + thread.getName() + "the error is" + throwable.getMessage());

            }
        }); // 처음부터 전체 스레드에 해당되는 예외 핸들러를 지정할 수 있다.
        thread.start();
    }
}
