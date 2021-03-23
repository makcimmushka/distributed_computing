package ensign;

import entity.Stuff;
import queue.StolenStuff;
import queue.Truck;

public class Petrov extends Ensign {
    private final Truck truck;
    private final StolenStuff stolenStuff;

    public Petrov(Truck truck, StolenStuff stolenStuff) {
        this.truck = truck;
        this.stolenStuff = stolenStuff;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Stuff stuff = this.stolenStuff.get();

                Thread.sleep(100);
                this.truck.put(stuff);

                System.out.println("Petrov moved to a truck: " + stuff);
            } catch (InterruptedException e) {
                System.out.println("Petrov's stealing process was interrupted");
                break;
            }
        }
    }
}