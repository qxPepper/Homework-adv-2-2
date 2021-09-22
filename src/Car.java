public class Car {
    private final static int MAX_COUNT_CAR = 15;
    private final static int DIVISOR= 3;
    private final static long TIME_SLEEP = 1000;
    private final static int SEC_TO_MIL = 1000;

    public void carFactory() {
        int countCar = 0;
        long current;
        long finish;
        long start = System.currentTimeMillis();

        while (countCar < MAX_COUNT_CAR) {
            try {
                Thread.sleep(TIME_SLEEP);
                finish = System.currentTimeMillis();
                current = (finish - start) / SEC_TO_MIL;

                if (current % DIVISOR == 0) {
                    Main.availableCar = true;
                    countCar++;
                } else {
                    Main.availableCar = false;
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }
    }
}
