package main.task2;

import java.util.Random;

public abstract class Person {
    protected int M;
    protected Buffer buffer;
    protected Random generator = new Random();
    protected long waitingTimeAverage;

    public Person(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
        waitingTimeAverage = 0;
    }

    public long getWaitingTimeAverage(){
        return waitingTimeAverage;
    }

    abstract public String retPersonType();
}
