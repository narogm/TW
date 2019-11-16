package main.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer extends Buffer{

    private int maxSize;
    private int currentlyTaken;
    private Lock lock;
    private Condition producersCondition;
    private Condition consumersCondition;


    public NaiveBuffer(int M) {
        this.maxSize = 2 * M;
        this.currentlyTaken = 0;
        lock = new ReentrantLock();
        producersCondition = lock.newCondition();
        consumersCondition = lock.newCondition();
    }

    public long put(int productSize){
        lock.lock();
        try {
            long startTime = System.nanoTime();
            while(productSize + currentlyTaken > maxSize) {
//                System.out.println("producer has to wait | val --> " + productSize);
                producersCondition.await();
            }
            long waitingTime = System.nanoTime() - startTime;
            currentlyTaken += productSize;
//            System.out.println("Producer has add to buffer val: " + productSize + "\ncurrently taken ---> " + currentlyTaken);
            consumersCondition.signalAll();
            return waitingTime;
        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.out.println("producer -- interrupt");
            return -1;
        } finally {
            lock.unlock();
        }
    }

    public long get(int productSize){
        lock.lock();
        try {
            long startTime = System.nanoTime();
            while(currentlyTaken < productSize) {
//                System.out.println("consumer has to wait | val --> " + productSize);
                consumersCondition.await();
            }
            long waitingTime = System.nanoTime() - startTime;
            currentlyTaken -= productSize;
//            System.out.println("Consumer has taken from buffer val: " + productSize + "\ncurrently taken ---> " + currentlyTaken);
            producersCondition.signalAll();
            return waitingTime;
        } catch (InterruptedException e) {
//            e.printStackTrace();
//            System.out.println("consumer -- interrupt");
            return -1;
        } finally {
            lock.unlock();
        }
    }

}
