package main.task1;

public class Counter {

    private int value;
    private BinarySemaphore semaphore;

    public Counter(int value, BinarySemaphore semaphore) {
        this.value = value;
        this.semaphore = semaphore;
    }

    public int getValue() {
        return value;
    }

    public void increment(){
        semaphore.take();
        this.value++;
        semaphore.release();
    }

    public void decrement(){
        semaphore.take();
        this.value--;
        semaphore.release();
    }
}
