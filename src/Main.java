public class Main {
    public static boolean availableCar;
    public static int countCar;

    private final static int TIME_SLEEP = 500;
    private final static int MAX_COUNT_BUYER = 10;

    public static void main(String[] args) throws InterruptedException {
        CarShop carShop = new CarShop();
        Car car = new Car();

        Thread factory = new Thread(car::carFactory, "Завод");
        factory.start();

        for (int i = 1; i <= MAX_COUNT_BUYER; i++) {
            String name = "Покупатель_" + i;
            new Thread(carShop::sellCar, name).start();
            Thread.sleep(TIME_SLEEP);
        }

        Thread trader = new Thread(carShop::receiveCar, "Автосалон");
        trader.start();
    }
}
