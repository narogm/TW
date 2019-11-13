package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args){

        final int threadsAmount = 2;
        final int tasksAmount = 2;
        ExecutorService service = Executors.newFixedThreadPool(threadsAmount);
        Mandelbrot mandelbrot = new Mandelbrot(tasksAmount);

        List<Future<MyThread>> results = new ArrayList<>();
        Collection<Callable<MyThread>> tasks = new ArrayList<>();

        for(int i=0; i<tasksAmount; i++){
            tasks.add(new MyThread(mandelbrot, i));
        }

//        for(int i=0; i<threadsAmount; i++){
//            service.execute(new MyThread(mandelbrot, i));
//        }
        try {
            results = service.invokeAll(tasks);
            service.shutdown();
            service.awaitTermination(5,TimeUnit.SECONDS);
            for(int i=0; i<results.size(); i++){
                System.out.println(results.get(0).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        mandelbrot.setVisible(true);

    }

    private void execute(){
        
    }
}
