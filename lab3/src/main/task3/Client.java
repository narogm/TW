package main.task3;

import static java.lang.Thread.sleep;

public class Client implements Runnable {

    private int id;
    private Waiter waiter;

    public Client(int id, Waiter waiter) {
        this.id = id;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            waiter.makeReservation(id);
            System.out.println("Klient o id " + id + " korzysta ze stolika");
            waiter.release();
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
