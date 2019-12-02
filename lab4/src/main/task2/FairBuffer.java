package main.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairBuffer extends Buffer{

    private int maxSize;
    private int currentlyTaken;
    private Lock lock;
    private Condition producersCondition;
    private Condition consumersCondition;
    private Condition firstProducerCondition;
    private Condition firstConsumerCondition;
    boolean producerEmpty;
    boolean consumerEmpty;


    public FairBuffer(int M) {
        this.maxSize = 2 * M;
        this.currentlyTaken = 0;
        lock = new ReentrantLock();
        producersCondition = lock.newCondition();
        consumersCondition = lock.newCondition();
        firstProducerCondition = lock.newCondition();
        firstConsumerCondition = lock.newCondition();
        producerEmpty = true;
        consumerEmpty = true;
    }

    public long put(int productSize){
        lock.lock();
        try {
            long startTime = System.nanoTime();
            if(!producerEmpty)
                producersCondition.await();
            producerEmpty = false;
            while(productSize + currentlyTaken > maxSize) {
//                System.out.println("producer has to wait | val --> " + productSize);
                firstProducerCondition.await();
            }
            long waitingTime = System.nanoTime() - startTime;
            currentlyTaken += productSize;
//            System.out.println("Producer has add to buffer val: " + productSize + "\ncurrently taken ---> " + currentlyTaken);
            producerEmpty = true;
            producersCondition.signal();
            firstConsumerCondition.signal();
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
            if(!consumerEmpty)
                consumersCondition.await();
            consumerEmpty = false;
            while(currentlyTaken < productSize) {
//                System.out.println("consumer has to wait | val --> " + productSize);
                firstConsumerCondition.await();
            }
            long waitingTime = System.nanoTime() - startTime;
            currentlyTaken -= productSize;
//            System.out.println("Consumer has taken from buffer val: " + productSize + "\ncurrently taken ---> " + currentlyTaken);
            consumerEmpty = true;
            consumersCondition.signal();
            firstProducerCondition.signal();
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

