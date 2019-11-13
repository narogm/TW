package main.task1;

public class MyThread extends Thread{
    private Counter counter;
    private boolean increm;

    public MyThread(Counter counter, boolean increm) {
        this.counter = counter;
        this.increm = increm;
    }

    public void run(){
        if (increm){
            for(int i=0; i<1000; i++){
                counter.increment();
                System.out.println("inkrementacja " + i);
            }
        }
        else
            for(int i=0; i<1000; i++){
                counter.decrement();
                System.out.println("dekrementacja " + i);
            }
    }
}

