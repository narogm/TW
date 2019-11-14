package main;

import java.util.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args){
        int[] threads = {1, 2, 4};
        Main app = new Main();
        for (int threadAmount: threads) {
            System.out.println("Threads amount --> " + threadAmount + "\n#################");

            System.out.println("Task amount the same as threads amount");
            for(int i=0; i<10; i++){
                Mandelbrot mandelbrot = new Mandelbrot(threadAmount);
                long tmp = System.nanoTime();
                app.execute(threadAmount, threadAmount, mandelbrot);
                System.out.println(System.nanoTime() - tmp);
            }
            System.out.println("-----------------------------");

            System.out.println("10 times more tasks than threads amount");
            for(int i=0; i<10; i++){
                Mandelbrot mandelbrot = new Mandelbrot(threadAmount);
                long tmp = System.nanoTime();
                app.execute(threadAmount, threadAmount*10, mandelbrot);
                System.out.println(System.nanoTime() - tmp);
            }
            System.out.println("-----------------------------");

            System.out.println("Each task is one pixel");
            for(int i=0; i<10; i++){
                Mandelbrot mandelbrot = new Mandelbrot(threadAmount);
                long tmp = System.nanoTime();
                app.execute(threadAmount, 600*800, mandelbrot);
                System.out.println(System.nanoTime() - tmp);
            }
            System.out.println("*****************************");
        }
    }

    void execute(int threadsAmount, int tasksAmount, Mandelbrot mandelbrot){
        ExecutorService service = Executors.newFixedThreadPool(threadsAmount);

        Collection<Callable<MyThread>> tasks = new ArrayList<>();

        for(int i=0; i<tasksAmount; i++){
            tasks.add(new MyThread(mandelbrot, i));
        }

        try {
            service.invokeAll(tasks);
            service.shutdown();
            service.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        mandelbrot.setVisible(true);
    }
}
