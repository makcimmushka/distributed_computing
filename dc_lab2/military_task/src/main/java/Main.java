import ensign.Ivanov;
import ensign.Necheporchuk;
import ensign.Petrov;

import queue.StolenStuff;
import queue.Truck;

public class Main {
    public static void main(String[] args) {
        StolenStuff stolenStuff = new StolenStuff();
        Truck truck = new Truck();
        Ivanov ivanov = new Ivanov(stolenStuff);
        Petrov petrov = new Petrov(truck, stolenStuff);
        Necheporchuk necheporchuk = new Necheporchuk(truck);

        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ivanov.finishPlunder();
        petrov.finishPlunder();
        necheporchuk.finishPlunder();
    }
}
