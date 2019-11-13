package main.task1;

public class Producer implements Runnable {

    Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i=0; i<200; i++){
            buffer.produce();
        }
    }
}
