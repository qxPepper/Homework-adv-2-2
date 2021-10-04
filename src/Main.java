public class Main {
    public static final int COUNT_BUYERS = 10;

    public static void main(String[] args) {
        CarShop carShop = new CarShop();

        for (int i = 1; i <= COUNT_BUYERS; i++) {
            new Thread(carShop::sellCar, "Покупатель_" + i).start();
        }

        new Thread(carShop::receiveCar, "Автопроизводитель").start();
    }
}

