package main;

import java.util.ArrayList;
import java.util.List;

public class Main{

    public static void main(String[] args) {
//        Counter counter = new Counter(0);
//
//        MyThread inc = new MyThread(counter, true);
//        MyThread dec = new MyThread(counter, false);
//        inc.start();
//        dec.start();
//
//        try{
//            inc.join();
//            dec.join();
//        }
//        catch (Exception e){
//            System.out.println("exception");
//        }
//        System.out.println(counter.getValue());

        Buffer buffer = new Buffer();

        List<Thread> threads = new ArrayList<Thread>();
        for(int i=0; i<3; i++){
            threads.add(new Thread(new Producer(buffer)));
            threads.get(i).start();
        }
        for(int i=0; i<3; i++){
            threads.add(new Thread(new Consumer(buffer)));
            threads.get(i+3).start();
        }

        try{
            for(int i=0; i<6; i++){
                threads.get(i).join();
                System.out.println("zakonczono watek " + i);
            }
        }
        catch (Exception e){
            System.out.println("exception");
        }
    }
}
