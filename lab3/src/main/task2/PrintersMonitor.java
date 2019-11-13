package main.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintersMonitor {

    private final Lock lock = new ReentrantLock();
    private int printersAmount;
    private int printersTaken;
    private Condition printersCondition;
    private Boolean[] printers;

    public PrintersMonitor(int printersAmount) {
        this.printersAmount = printersAmount;
        this.printersTaken = 0;
        this.printersCondition = lock.newCondition();
        this.printers = new Boolean[printersAmount];
        for(int i=0; i<printersAmount; i++){
            printers[i] = false;
        }
    }

    public int makeReservation() {
        lock.lock();
        try{
            while (printersAmount == printersTaken){
                printersCondition.await();
                System.out.println("oczekiwanie");
            }
            printersTaken++;
            for(int i=0; i<printersAmount; i++){
                if(!printers[i]){
                    printers[i] = true;
                    return i;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return -1;
    }

    public void release(int nr_drukarki) {
        lock.lock();
        printersTaken--;
        printers[nr_drukarki] = false;
        printersCondition.signal();
        lock.unlock();
    }
}
