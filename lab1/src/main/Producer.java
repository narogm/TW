package main;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        for(int i = 0;  i < 100000;   i++) {
            buffer.put("message "+i);
        }
    }
}

