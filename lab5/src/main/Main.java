package main;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args){

        final int threadsAmount = 10;
        final int tasksAmount = 1;
        ExecutorService service = Executors.newFixedThreadPool(threadsAmount);
        Mandelbrot mandelbrot = new Mandelbrot(threadsAmount);
        for(int i=0; i<threadsAmount; i++){
            service.execute(new MyThread(mandelbrot, i));
        }
        service.shutdown();
        try {
            service.awaitTermination(5,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mandelbrot.setVisible(true);

    }
}
