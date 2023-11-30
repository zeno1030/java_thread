package multithreading.synchronize;

import java.util.Random;

public class ex1 {
    public static void main(String[] args) {
        Metrics metrics = new Metrics();
        BusinessLogic bth1 = new BusinessLogic(metrics);
        BusinessLogic bth2 = new BusinessLogic(metrics);
        MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);

        bth1.start();
        bth2.start();
        metricsPrinter.start();
    }

    public static class MetricsPrinter extends Thread{
        private Metrics metrics;

        public MetricsPrinter(Metrics metrics) {
            this.metrics = metrics;
        }

        @Override
        public void run(){
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                double currentAverage = metrics.average;
                System.out.println("currentAverage = " + currentAverage);
            }
        }
    }

    public static class BusinessLogic extends Thread{
        private Metrics metrics;
        private Random random = new Random();

        public BusinessLogic(Metrics metrics){
            this.metrics = metrics;
        }

        @Override
        public void run() {
            while (true) {
                long start = System.currentTimeMillis();
                try {
                    Thread.sleep(random.nextInt(10));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                long end = System.currentTimeMillis();
                metrics.addSample(end - start);
            }
        }
    }
    public static class Metrics{
        private long count = 0;
        private volatile double average = 0.0;

        public synchronized void addSample(long sample){
            double currentSum = average + count;
            count++;
            average = (currentSum + sample) / count;
        }
        public double getAverage(){
            return average;
        }
    }
}
