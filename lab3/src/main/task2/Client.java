package main.task2;

public class Client implements Runnable {

    private int nr_klienta;
    private PrintersMonitor monitor;

    public Client(int nr_klienta, PrintersMonitor monitor) {
        this.nr_klienta = nr_klienta;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            int nr_drukarki = monitor.makeReservation();
            System.out.println("Klient nr " + nr_klienta + " korzysta z drukarki " + nr_drukarki);
            monitor.release(nr_drukarki);
        }
    }
}
