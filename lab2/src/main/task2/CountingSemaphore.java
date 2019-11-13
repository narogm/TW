package main.task2;

public class CountingSemaphore {
    private int counter;

    public CountingSemaphore(int counter) {
        this.counter = counter;
    }

    public synchronized void take(){
        while (counter < 1){
            try {
                System.out.println("oczekiwanie");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        counter--;
        System.out.println("stan semafora: " + counter);
    }

    public synchronized void release(){
        counter++;
        System.out.println("stan semafora: " + counter);
        notifyAll();
    }
}
