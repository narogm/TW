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
                long result = app.execute(threadAmount, threadAmount, mandelbrot);
                System.out.println(result/threadAmount);
            }
            System.out.println("-----------------------------");

            System.out.println("10 times more tasks than threads amount");
            for(int i=0; i<10; i++){
                Mandelbrot mandelbrot = new Mandelbrot(threadAmount);
                long result = app.execute(threadAmount, threadAmount*10, mandelbrot);
                System.out.println(result/threadAmount/10);
            }
//            System.out.println("-----------------------------");
            //TODO zly podzial pola (na paski) przy zadaniu na kazdy pixel trzeba inaczej podzielic

//            System.out.println("Each task is one pixel");
//            for(int i=0; i<10; i++){
//                Mandelbrot mandelbrot = new Mandelbrot(threadAmount);
//                long result = app.execute(threadAmount, 600*800, mandelbrot);
//                System.out.println(result/600/800);
//            }
            System.out.println("*****************************");
        }
    }

    long execute(int threadsAmount, int tasksAmount, Mandelbrot mandelbrot){
        ExecutorService service = Executors.newFixedThreadPool(threadsAmount);

        List<Future<MyThread>> results = new ArrayList<>();
        Collection<Callable<MyThread>> tasks = new ArrayList<>();

        for(int i=0; i<tasksAmount; i++){
            tasks.add(new MyThread(mandelbrot, i));
        }

        long average = 0;
        try {
            results = service.invokeAll(tasks);
            service.shutdown();
            service.awaitTermination(5,TimeUnit.SECONDS);
            for(int i=0; i<results.size(); i++){
                average += Long.parseLong(String.valueOf(results.get(i).get()));
//                System.out.println(Long.valueOf(String.valueOf(results.get(i).get())));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
//        mandelbrot.setVisible(true);
        return average;
    }
}
