package main.task3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Waiter {

    private final Lock lock = new ReentrantLock();
    private Condition[] pairConditions;
    private int[] pairs;
    private Condition isTableFree;
    private int pairAtTable;

    public Waiter(int pairsAmount) {
        this.isTableFree = lock.newCondition();
        this.pairAtTable = -1;
        pairConditions = new Condition[pairsAmount];
        for(int i=0; i<pairsAmount; i++){
            pairConditions[i] = lock.newCondition();
        }

        pairs = new int[pairsAmount];
        for(int i=0; i<pairsAmount; i++){
            pairs[i] = 0;
        }
    }

    void makeReservation(int id) {
        lock.lock();
        try{
            pairs[id] ++;
            if (pairs[id] < 2){
                System.out.println("osoba z pary " + id + " oczekuje na druga osobe");
                pairConditions[id].await();
            } else {
                System.out.println("przyszla druga osoba z pary " + id);
                while(pairAtTable != -1){
                    isTableFree.await();
                }
                System.out.println("Para o id " + id + "dostala stolik");
                pairAtTable = id;
                pairConditions[id].signal();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        pairs[pairAtTable]--;
        if (pairs[pairAtTable] == 0){
            isTableFree.signal();
            pairConditions[pairAtTable].signal();
            System.out.println("para o id " + pairAtTable + "zwalnia stolik\n");
            pairAtTable = -1;
        }
        else {
            try {
                pairConditions[pairAtTable].await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lock.unlock();
    }
}
