package main;

import main.task1.Buffer;
import main.task1.Consumer;
import main.task1.Converter;
import main.task1.Producer;

//import main.task2.Buffer;
//import main.task2.Person;
//import main.task2.Producer;
//import main.task2.Consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        //task1

        final int N = 100;
        final int convertersAmount = 5;

        Buffer buffer = new Buffer(100, convertersAmount);

        List<Thread> threads = new ArrayList<Thread>();

        for(int i=0; i<convertersAmount; i++){
            threads.add(new Thread(new Converter(buffer, i + 1)));
            threads.get(i).start();
        }

        threads.add(new Thread(new Producer(buffer)));
        threads.get(convertersAmount).start();

        threads.add(new Thread(new Consumer(buffer)));
        threads.get(convertersAmount+1).start();

        try{
            for(int i=0; i<convertersAmount + 2; i++){
                threads.get(i).join();
            }
        }
        catch (Exception e){
            System.out.println("exception");
        }

//        w zad 2 nie sugerowac sie konkretnym miejscem w tablicy, tylko ogolnie iloscia dostepnego miejsca
//
//        final int M = 1000;
//        final int producersAmount = 10;
////        final int consumersAmount = 10;
//
//        Buffer buffer = new Buffer(M);
//
//        ExecutorService service = Executors.newFixedThreadPool(20);
//        List<Future<Person>> producersResult = new ArrayList<>();
//        List<Future<Person>> consumersResult = new ArrayList<>();
//
//        Collection<Callable<Person>> tasks = new ArrayList<>();
//
//        for (int i = 0; i < producersAmount; i++) {
////            producers.add(new Producer(M, buffer));
////            consumers.add(new Consumer(M, buffer));
////            producersResult.add(service.submit(new Producer(M, buffer)));
////            consumersResult.add(service.submit(new Consumer(M, buffer)));
//            tasks.add(new Producer(M, buffer));
//            tasks.add(new Consumer(M, buffer));
//        }
//
//        try {
////            TimeUnit.SECONDS.sleep(2);
////            service.shutdown();
////            service.awaitTermination(1, TimeUnit.SECONDS);
////            service.shutdownNow();
//            List<Future<Person>> results = service.invokeAll(tasks, 2, TimeUnit.SECONDS);
//            System.out.println(results.get(0).get().retPersonType());
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//
//
////        for (int i = producersAmount; i < consumersAmount + producersAmount; i++) {
////            threads.add(new Thread(new Consumer(M, buffer)));
////            threads.get(i).start();
////        }
//
////        List<Thread> threads = new ArrayList<Thread>();
////
////        for(int i=0; i<producersAmount; i++){
////            threads.add(new Thread(new Producer(M, buffer)));
////            threads.get(i).start();
////        }
////
////        for(int i=producersAmount; i<consumersAmount+producersAmount; i++){
////            threads.add(new Thread(new Consumer(M, buffer)));
////            threads.get(i).start();
////        }
////
////        try{
////            for(int i=0; i<producersAmount+consumersAmount; i++){
////                threads.get(i).join();
////            }
////        }
////        catch (Exception e){
////            System.out.println("exception");
////        }

    }
}
