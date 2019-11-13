package main.task2;

import java.util.ArrayList;
import java.util.List;

public class ShopSimulator {

    private CountingSemaphore semaphore;
    private int clientsAmount;

    public ShopSimulator(CountingSemaphore semaphore, int clientsAmount) {
        this.semaphore = semaphore;
        this.clientsAmount = clientsAmount;
    }

    public void take(){
        semaphore.take();
        System.out.println("wzieto koszyk\n");
    }

    public void giveBack(){
        semaphore.release();
        System.out.println("oddano koszyk\n");
    }

    public void simulate(){
        List<Thread> threads = new ArrayList<Thread>();
        for(int i=0; i<4; i++){
            threads.add(new Thread(new Client(this)));
            threads.get(i).start();
        }


        try{
            for(int i=0; i<4; i++){
                threads.get(i).join();
                System.out.println("zakonczono watek " + i);
            }
        }
        catch (Exception e){
            System.out.println("exception");
        }
    }
}
