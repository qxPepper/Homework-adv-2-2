import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private final static int NUMBER_OF_ATTEMPTS = 3;
    private final static int TIME_SLEEP = 1000;
    private final static int MAX_COUNT_CAR = 10;

    private boolean availableCar;

    Lock lock = new ReentrantLock(true);
    Condition carCondition = lock.newCondition();

    public void sellCar() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " решил купить автомобиль.");

            carCondition.await();

            if (availableCar) {
                for (int i = 0; i < NUMBER_OF_ATTEMPTS; i++) {
                    if (Main.countCar == MAX_COUNT_CAR) {
                        return;
                    }
                    System.out.println(Thread.currentThread().getName() + " зашёл в автосалон.");

                    if (!Main.availableCar) {
                        System.out.println(Thread.currentThread().getName() + " - Машин нет. Ждите когда привезут.");
                        Thread.sleep(TIME_SLEEP);

                    } else {
                        Thread.sleep(TIME_SLEEP);
                        System.out.println("Производитель Tesla выпустил 1 авто.");
                        System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто!");
                        Main.availableCar = false;
                        Main.countCar++;
                        Thread.sleep(TIME_SLEEP);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //Здесь даём старт продаж.
    public void receiveCar() {
        lock.lock();
        try {
            while (!Main.availableCar) {
                Thread.sleep(TIME_SLEEP);
            }
            availableCar = true;

            carCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

