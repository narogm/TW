package main;

public class MyThread extends Thread{
    private Counter counter;
    private boolean increm;

    public MyThread(Counter counter, boolean increm) {
        this.counter = counter;
        this.increm = increm;
    }

    public void run(){
        if (increm){
            for(int i=0; i<100000000; i++){
                counter.increment();
            }
        }
        else
            for(int i=0; i<100000000; i++){
                counter.decrement();
            }
    }
}
