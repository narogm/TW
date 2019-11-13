package main.task1;

public class BinarySemaphore {
    private boolean isFree;

    public BinarySemaphore() {
        this.isFree = true;
    }

    public synchronized void take(){
        while (!isFree()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        isFree = false;
    }

    public synchronized void release(){
        isFree = true;
        notifyAll();
    }

    public boolean isFree(){
        return isFree;
    }
}
