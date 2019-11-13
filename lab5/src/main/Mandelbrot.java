package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame{

    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    private Lock lock = new ReentrantLock();

    private int threadsAmount;

    public Mandelbrot(int threadsAmount) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        this.threadsAmount = threadsAmount;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public void calculate(int threadID) {
        System.out.println("ThreadID: " + threadID +
                "\n" + getHeight()/threadsAmount * threadID + " -- " + getHeight()/threadsAmount * (threadID + 1) + "\n\n");
        for (int y = getHeight()/threadsAmount * threadID; y < getHeight()/threadsAmount * (threadID + 1); y++) {
            for (int x = 0; x < getWidth(); x++) {
                lock.lock();
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                lock.unlock();
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
    }
}