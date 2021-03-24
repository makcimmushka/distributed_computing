import java.util.concurrent.Semaphore;

class Barber implements Runnable {
    private final Semaphore barberChair;

    private boolean isSleeping;

    public Barber(Semaphore barberChair, boolean isSleeping) {
        this.barberChair = barberChair;
        this.isSleeping = isSleeping;
    }

    @Override
    public void run() {
        while (true) {
            cutHair();
        }
    }

    void cutHair() {
        if (isSleeping) {
            System.out.println("The barber is sleeping...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            if (barberChair.tryAcquire()){
                isSleeping = true;
            } else {
                System.out.println("The barber has cut the hair");
                barberChair.release();
            }
        }
    }

    void wakeUp() {
        if (isSleeping) {
            isSleeping = false;
            //barberChair.release();
            System.out.println("The barber has woken up");
        }
    }
}
