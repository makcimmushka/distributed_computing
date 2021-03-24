import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) {
        Semaphore waitingChairs = new Semaphore(3, true);
        Semaphore barberChair = new Semaphore(1);

        new Thread(new Barbershop(barberChair, waitingChairs)).start();
    }
}
