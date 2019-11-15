package main.task2;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public abstract class Person {
    protected int M;
    protected Buffer buffer;
    protected Random generator = new Random();
//    protected long waitingTimeAverage;
    protected Map<Integer, Long> times;

    public Person(int m, Buffer buffer) {
        M = m;
        this.buffer = buffer;
//        waitingTimeAverage = 0;
        times = new HashMap<>();
    }

//    public long getWaitingTimeAverage(){
//        return waitingTimeAverage;
//    }


    public Map<Integer, Long> getTimes() {
        return times;
    }

    abstract public String retPersonType();
}
