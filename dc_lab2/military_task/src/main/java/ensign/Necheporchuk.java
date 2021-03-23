package ensign;

import queue.Truck;

public class Necheporchuk extends Ensign {
    private final Truck truck;
    private int sum;

    public Necheporchuk(Truck truck) {
        this.truck = truck;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                this.sum += this.truck.get().cost;

                Thread.sleep(100);
                System.out.println("Necheporchuk recounted total gain, it's: " + this.sum);
            } catch (InterruptedException e) {
                System.out.println("Necheporchuk's stealing process was interrupted");
                break;
            }
        }
    }
}

