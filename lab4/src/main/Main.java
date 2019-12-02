package main;

//import main.task1.Buffer;
//import main.task1.Consumer;
//import main.task1.Converter;
//import main.task1.Producer;

import main.task2.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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


        app.task_2();
    }

    void task_2(){
        final int M = 1000;
        final int producersAmount = 10;

//        NaiveBuffer buffer = new NaiveBuffer(M);
        FairBuffer buffer = new FairBuffer(M);

        ExecutorService service = Executors.newFixedThreadPool(2*producersAmount);
        List<Future<Person>> producersResult = new ArrayList<>();
        List<Future<Person>> consumersResult = new ArrayList<>();


        for (int i = 0; i < producersAmount; i++) {
            producersResult.add(service.submit(new Producer(M, buffer)));
            consumersResult.add(service.submit(new Consumer(M, buffer)));
        }

        service.shutdown();
        try {
            if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }

//            System.out.println("Producers:");
            FileWriter producersWriter = new FileWriter("producers_fair_10.txt");
            for(int i=0; i<producersAmount; i++){
                Producer producer = (Producer) producersResult.get(i).get();
//                producer.getTimes().forEach((k, v) -> System.out.println(k + "    " + v));
                producer.getTimes().forEach((k, v) -> {
                    try {
                        producersWriter.write(k + "    " + v + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
//                System.out.println("***********************************");
            }
            producersWriter.close();

//            System.out.println("\n###################################\n");
//            System.out.println("Consumers:");

            FileWriter consumersWriter = new FileWriter("consumers_fair_10.txt");
            for(int i=0; i<producersAmount; i++){
                Consumer consumer = (Consumer) consumersResult.get(i).get();
//                consumer.getTimes().forEach((k, v) -> System.out.println(k + "    " + v));
                consumer.getTimes().forEach((k, v) -> {
                    try {
                        consumersWriter.write(k + "    " + v + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
//                System.out.println("***********************************");
            }
        } catch (InterruptedException | ExecutionException | IOException e) {
            service.shutdownNow();
        }
    }
}
