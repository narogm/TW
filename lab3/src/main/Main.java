package main;

import main.task2.PrintersMonitor;
import main.task3.Client;
import main.task3.Waiter;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//    task2
//        final int printersAmount = 5;
//        final int clientsAmount = 10;
//
//        PrintersMonitor monitor = new PrintersMonitor(printersAmount);
//
//        List<Thread> threads = new ArrayList<Thread>();
//        for(int i=0; i<clientsAmount; i++){
//            threads.add(new Thread(new Client(i, monitor)));
//            threads.get(i).start();
//        }
//
//        try{
//            for(int i=0; i<clientsAmount; i++){
//                threads.get(i).join();
////                System.out.println("zakonczono watek " + i);
//            }
//        }
//        catch (Exception e){
//            System.out.println("exception");
//        }

//    task3
        final int pairsAmount = 10;

        Waiter waiter = new Waiter(pairsAmount);

        List<Thread> threads = new ArrayList<Thread>();
        for(int i=0; i<pairsAmount * 2; i++){
            threads.add(new Thread(new Client(i / 2, waiter)));
            threads.get(i).start();
        }

        try{
            for(int i=0; i<pairsAmount * 2; i++){
                threads.get(i).join();
//                System.out.println("zakonczono watek " + i);
            }
        }
        catch (Exception e){
            System.out.println("exception");
        }
    }
}
