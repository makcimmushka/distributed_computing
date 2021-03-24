import java.util.concurrent.Semaphore;

class Client implements Runnable {
    private final Semaphore waitingChairs;
    private final Semaphore barberChair;

    private final long id;
    private final Barber barber;

    Client(long id, Semaphore barberChair, Semaphore waitingChairs, Barber barber) {
        this.id = id;
        this.barberChair = barberChair;
        this.waitingChairs = waitingChairs;
        this.barber = barber;
    }

    @Override
    public void run() {
        if (waitingChairs.tryAcquire()) {
            System.out.println("Client " + id + " came to barbershop");
            if (!barberChair.tryAcquire()) {
                System.out.println("Client " + id + " set in barber chair");
                barber.wakeUp();
            }
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            waitingChairs.release();
        } else {
            System.out.println("No free sits");
        }
    }
}

