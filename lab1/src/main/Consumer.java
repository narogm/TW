package main;

class Consumer implements Runnable {
    private Buffer buffer;

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 100000;   i++) {
            String message = buffer.take();
            if (i == 99999) System.out.println(message);
        }

    }
}