package main.task1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {

    private int bufferSize;
    private int[] buffer;
    private Lock lock = new ReentrantLock();
    private Condition producerCondition = lock.newCondition();
    private Condition[] convertersCondition;
    private Condition consumerCondition = lock.newCondition();

    private int convertersAmount;
    private int producerPosition;
    private int consumerPosition;
    private int[] positions;

    public Buffer(int N, int convertersAmount) {
        this.bufferSize = N;
        buffer = new int[N];
        for(int i=0; i<N; i++)
            buffer[i] = -1;

        this.convertersAmount = convertersAmount;
        convertersCondition = new Condition[convertersAmount];
        for(int i=0; i<convertersAmount; i++)
            convertersCondition[i] = lock.newCondition();

        producerPosition = 0;
        consumerPosition = 0;
        positions = new int[convertersAmount];
        for(int i=0; i<convertersAmount; i++){
            positions[i] = 0;
        }
    }

    public void produce(){
        lock.lock();
            try {
                if(buffer[producerPosition] != -1) {
                    System.out.println("Producent oczekuje na dostep do pola");
                    producerCondition.await();
                }
                buffer[producerPosition] = 0;
                System.out.println("wyprodukowano produkt na pozycji " + producerPosition);
                producerPosition = (producerPosition + 1) % bufferSize;
                convertersCondition[0].signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
    }

    public void convert(int id){
        lock.lock();
        try {
            if(buffer[positions[id-1]] != (id-1)) {
                System.out.println("Konwerter o id " + id + " oczekuje na dostep do pola");
                convertersCondition[id-1].await();
            }
            buffer[positions[id-1]] = id;
            System.out.println("skonwertowano produkt na pozycji " + positions[id-1] + " --> wartosc " + id);
            positions[id-1] = (positions[id-1] + 1) % bufferSize;
            if(id == convertersAmount){
                consumerCondition.signal();
            } else {
                convertersCondition[id].signal();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consume(){
        lock.lock();
        try {
            if(buffer[consumerPosition] != convertersAmount) {
                System.out.println("Konsument oczekuje na dostep do pola");
                consumerCondition.await();
            }
            buffer[consumerPosition] = -1;
            System.out.println("skomnumowano produkt na pozycji " + consumerPosition);
            consumerPosition = (consumerPosition + 1) % bufferSize;
            producerCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
