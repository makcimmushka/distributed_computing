public class Bee implements Runnable {
    private final Pot pot;
    private volatile boolean isActive;
    private final Bear bear;

    public Bee(Pot pot, Bear bear) {
        this.pot = pot;
        this.isActive = true;
        this.bear = bear;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            pot.addHoney();

            if (pot.isFull()) {
                bear.wakeUpBear();
            }

            isActive = false;
        }
    }

}