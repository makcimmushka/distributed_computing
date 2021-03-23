package ensign;

import entity.Stuff;
import queue.StolenStuff;
import util.StuffGenerator;

public class Ivanov extends Ensign {
    private final StolenStuff stolenStuff;

    public Ivanov(StolenStuff stolenStuff) {
        this.stolenStuff = stolenStuff;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Stuff stuff = StuffGenerator.generate();
                this.stolenStuff.put(stuff);

                System.out.println("Ivanov stole: " + stuff);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Ivanov's stealing process was interrupted");
                break;
            }
        }
    }
}

