package main;

//import main.task1.Buffer;
//import main.task1.Consumer;
//import main.task1.Converter;
//import main.task1.Producer;

import main.task2.Buffer;
import main.task2.Person;
import main.task2.Producer;
import main.task2.Consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();
        //task1

//        final int N = 100;
//        final int convertersAmount = 5;
//
//        Buffer buffer = new Buffer(100, convertersAmount);
//
//        List<Thread> threads = new ArrayList<Thread>();
//
//        for(int i=0; i<convertersAmount; i++){
//            threads.add(new Thread(new Converter(buffer, i + 1)));
//            threads.get(i).start();
//        }
//
//        threads.add(new Thread(new Producer(buffer)));
//        threads.get(convertersAmount).start();
//
//        threads.add(new Thread(new Consumer(buffer)));
//        threads.get(convertersAmount+1).start();
//
//        try{
//            for(int i=0; i<convertersAmount + 2; i++){
//                threads.get(i).join();
//            }
//        }
//        catch (Exception e){
//            System.out.println("exception");
//        }

        app.taks_2();
    }

    void taks_2(){
        final int M = 10000;
        final int producersAmount = 100;
//        final int consumersAmount = 10;

        Buffer buffer = new Buffer(M);

        ExecutorService service = Executors.newFixedThreadPool(2*producersAmount);
        List<Future<Person>> producersResult = new ArrayList<>();
        List<Future<Person>> consumersResult = new ArrayList<>();


        for (int i = 0; i < producersAmount; i++) {
            producersResult.add(service.submit(new Producer(M, buffer)));
            consumersResult.add(service.submit(new Consumer(M, buffer)));
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(4, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
//            System.out.println(producersResult.get(0).get().retPersonType());

            System.out.println("Producers:");
            for(int i=0; i<producersAmount; i++){
                Producer producer = (Producer) producersResult.get(i).get();
                producer.getTimes().forEach((k, v) -> System.out.println(k + "    " + v));
//                System.out.println("***********************************");
            }

            System.out.println("\n###################################\n");
            System.out.println("Consumers:");
            for(int i=0; i<producersAmount; i++){
                Consumer consumer = (Consumer) consumersResult.get(i).get();
                consumer.getTimes().forEach((k, v) -> System.out.println(k + "    " + v));
//                System.out.println("***********************************");
            }
        } catch (InterruptedException | ExecutionException e) {
            service.shutdownNow();
        }
    }
}
