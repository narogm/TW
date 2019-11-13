package main.task1;

public class Converter implements Runnable {

    private Buffer buffer;
    private int id;

    public Converter(Buffer buffer, int id) {
        this.buffer = buffer;
        this.id = id;
    }

    @Override
    public void run() {
        for(int i=0; i<200; i++){
            buffer.convert(id);
        }
    }
}
