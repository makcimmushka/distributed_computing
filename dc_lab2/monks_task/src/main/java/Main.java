import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        final int monksNumber = 5;

        List<Monk> firstMonastery = new ArrayList<>();
        List<Monk> secondMonastery = new ArrayList<>();

        for (int i = 0; i < monksNumber; i++) {
            Monk monkForFirstMonastery = new Monk("UN");
            System.out.println(monkForFirstMonastery + " energy is: " + monkForFirstMonastery.energy);
            firstMonastery.add(monkForFirstMonastery);

            Monk monkForSecondMonastery = new Monk("IN");
            System.out.println(monkForSecondMonastery + " energy is: " + monkForSecondMonastery.energy);
            secondMonastery.add(monkForSecondMonastery);
        }

        System.out.println();

        Battlefield battlefield = new Battlefield(firstMonastery, secondMonastery, 0, monksNumber - 1);
        ForkJoinPool pool = new ForkJoinPool();
        Monk winner = pool.invoke(battlefield);

        System.out.println("Winner: " + winner);
    }
}
