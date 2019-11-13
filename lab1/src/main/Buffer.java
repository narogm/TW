package main;

public class Buffer {

    private String value;
    private boolean empty;

    public Buffer() {
        this.empty = true;
    }

    public synchronized void put(String value){
        while(!isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.value = value;
        System.out.println("wlozono wartosc");
        empty = false;
        notifyAll();
    }

    public synchronized String take(){
        while(isEmpty()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        empty = true;
        System.out.println("pobrano wartosc");
        notifyAll();
        return value;
    }

    public boolean isEmpty(){
        return empty;
    }
}
