package main;

import main.task1.BinarySemaphore;
import main.task1.Counter;
import main.task1.MyThread;
import main.task2.Client;
import main.task2.CountingSemaphore;
import main.task2.ShopSimulator;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        task 1
//        BinarySemaphore semaphore = new BinarySemaphore();
//        Counter counter = new Counter(0, semaphore);
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



//        task 2
        CountingSemaphore countingSemaphore = new CountingSemaphore(3);
        ShopSimulator shop = new ShopSimulator(countingSemaphore, 4);
        shop.simulate();

    }
}
