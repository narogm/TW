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
        for(int i=0; i<Integer.MAX_VALUE; i++){
            int val = generator.nextInt(M) + 1;
            waitingTimeAverage = (waitingTimeAverage*i + buffer.put(val))/(i+1);
        }
        System.out.println("\n--------- producer has finished --> " + waitingTimeAverage + " ------------\n");
        return this;
    }


}
