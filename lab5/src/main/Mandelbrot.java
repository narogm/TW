package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame{

    private BufferedImage I;
    int MAX_ITER = 570;
    private Lock lock = new ReentrantLock();

    private int tasksAmount;

    public Mandelbrot(int tasksAmount) {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        this.tasksAmount = tasksAmount;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public void calculate(int threadID) {

        //each pixel is one task
        if(tasksAmount == 600*800){
            int y = threadID/600;
            int x = threadID - y*600;
            double zy;
            double zx = zy = 0;
            double ZOOM = 150;
            double cX = (x - 400) / ZOOM;
            double cY = (y - 300) / ZOOM;
            int iter = MAX_ITER;
            while (zx * zx + zy * zy < 4 && iter > 0) {
                double tmp = zx * zx - zy * zy + cX;
                zy = 2.0 * zx * zy + cY;
                zx = tmp;
                iter--;
            }
            I.setRGB(x, y, iter | (iter << 8));
        }
        else {
            for (int y = getHeight()/ tasksAmount * threadID; y < getHeight()/ tasksAmount * (threadID + 1); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    double zy;
                    double zx = zy = 0;
                    double ZOOM = 150;
                    double cX = (x - 400) / ZOOM;
                    double cY = (y - 300) / ZOOM;
                    int iter = MAX_ITER;
                    while (zx * zx + zy * zy < 4 && iter > 0) {
                        double tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter--;
                    }
                    I.setRGB(x, y, iter | (iter << 8));
                }
            }

        }
    }
}