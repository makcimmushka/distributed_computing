import java.util.concurrent.Semaphore;

class Barbershop implements Runnable{
    Barber barber;
    private final Semaphore waitingChairs;
    private final Semaphore barberChair;

    public Barbershop(Semaphore barberChair, Semaphore waitingChairs) {
        this.barberChair = barberChair;
        this.waitingChairs = waitingChairs;
    }

    @Override
    public void run() {
        barber = new Barber(barberChair, false);
        new Thread(barber).start();

        long i = 0L;
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Client client = new Client(i, barberChair, waitingChairs, barber);
            new Thread(client).start();
            i++;
        }
    }
}
