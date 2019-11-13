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
        for(int i=0; i<Integer.MAX_VALUE; i++){
            int val = generator.nextInt(M) + 1;
            waitingTimeAverage = (waitingTimeAverage*i + buffer.get(val))/(i+1);
        }
        System.out.println("\n--------- consumer has finished --> " + waitingTimeAverage + " ------------\n");
        return this;
    }
}
