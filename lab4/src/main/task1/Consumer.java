package main.task1;

public class Consumer implements Runnable {

    Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        for(int i=0; i<200; i++){
            buffer.consume();
        }
    }
}
