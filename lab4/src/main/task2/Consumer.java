package main.task2;

import java.util.concurrent.Callable;

public class Consumer extends Person implements Callable {

    public Consumer(int m, Buffer buffer) {
        super(m, buffer);
    }

    @Override
    public String retPersonType() {
        return "consumer";
    }

    @Override
    public Object call() {
        int i=0;
        while(!Thread.currentThread().isInterrupted()){
            int val = generator.nextInt(M) + 1;
            long tmp = buffer.get(val);
            if(tmp != 0)
                times.put(val, tmp);
//                waitingTimeAverage = (waitingTimeAverage*i + tmp)/(i+1);
            else
                break;
            i++;
        }
//        System.out.println("\n--------- consumer has finished --> " + waitingTimeAverage + " ------------\n");
        return this;
    }
}
