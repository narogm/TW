package main;

public class Counter {

    private int value;

    public Counter(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public synchronized void increment(){
        this.value++;
    }

    public synchronized void decrement(){
        this.value--;
    }
}
