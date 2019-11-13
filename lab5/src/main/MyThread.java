package main;

public class MyThread implements Runnable {

    private Mandelbrot mandelbrot;
    private int threadID;

    public MyThread(Mandelbrot mandelbrot, int threadID) {
        this.mandelbrot = mandelbrot;
        this.threadID = threadID;
    }

    @Override
    public void run() {
//        if(threadID == 5)
        mandelbrot.calculate(threadID);
    }
}
