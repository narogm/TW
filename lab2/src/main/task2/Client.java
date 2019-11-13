package main.task2;

public class Client implements Runnable {
    private ShopSimulator shop;

    public Client(ShopSimulator shop) {
        this.shop = shop;
    }

    @Override
    public void run() {
        for(int i=0; i<10; i++){
            shop.take();
            shop.giveBack();
        }
    }
}
