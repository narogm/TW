package main.task2;

import java.util.concurrent.Callable;

public class Producer extends Person implements Callable {

    public Producer(int m, Buffer buffer) {
        super(m, buffer);
    }

    @Override
    public String retPersonType() {
        return "producer";
    }

    @Override
    public Object call() {
        int i=0;
        while(!Thread.currentThread().isInterrupted()){
            int val = generator.nextInt(M) + 1;
            long tmp = buffer.put(val);
            if(tmp != 0)
                times.put(val, tmp);
//                waitingTimeAverage = (waitingTimeAverage*i + tmp)/(i+1);
            else
                break;
            i++;
        }
//        System.out.println("\n--------- producer has finished --> " + waitingTimeAverage + " ------------\n");
        return this;
    }


}
