package main;

import java.util.concurrent.Callable;

public class MyThread implements Callable {

    private Mandelbrot mandelbrot;
    private int threadID;

    public MyThread(Mandelbrot mandelbrot, int threadID) {
        this.mandelbrot = mandelbrot;
        this.threadID = threadID;
    }

    @Override
    public Object call() {
        mandelbrot.calculate(threadID);
        return 0;
    }
}
