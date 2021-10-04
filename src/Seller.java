import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seller {
    private static final int COUNT_CARS = 10;
    private static final int DIVISOR = 3;
    private final static int TIME_SLEEP = 1000;

    private boolean availableCar;

    Lock lock = new ReentrantLock(true);
    Condition carCondition = lock.newCondition();

    public void sellCar() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " зашел в автосалон.");
            while (!availableCar) {
                System.out.println("Ответ автосалона для " + Thread.currentThread().getName() +
                        " - Машин нет.");

                carCondition.await();
            }
            Thread.sleep(TIME_SLEEP);
            System.out.println(Thread.currentThread().getName() + " уехал на новеньком авто!");
            availableCar = false;

            carCondition.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void receiveCar() {
        lock.lock();
        try {
            int countCar = 0;
            long current;
            long finish;
            long start = System.currentTimeMillis();

            while (countCar < COUNT_CARS) {
                Thread.sleep(TIME_SLEEP);
                finish = System.currentTimeMillis();
                current = (finish - start) / TIME_SLEEP;

                if (current % DIVISOR == 0) {
                    availableCar = true;
                    System.out.println("Производитель Tesla выпустил 1 авто.");
                    countCar++;

                    carCondition.signalAll();

                    carCondition.await();

                } else {
                    availableCar = false;
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

