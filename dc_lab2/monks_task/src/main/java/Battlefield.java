import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Battlefield extends RecursiveTask<Monk> {
    private final List<Monk> firstMonastery;
    private final List<Monk> secondMonastery;

    private final int start;
    private final int end;

    public Battlefield(List<Monk> firstMonastery,
                       List<Monk> secondMonastery,
                       int start, int end) {
        this.firstMonastery = firstMonastery;
        this.secondMonastery = secondMonastery;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Monk compute() {
        if (end - start == 0) {
            Monk firstMonk = firstMonastery.get(start);
            Monk secondMonk = secondMonastery.get(start);

            System.out.println("Fight: " + firstMonk + " and " + secondMonk);
            return Monk.max(firstMonk, secondMonk);
        }

        Battlefield leftHalf = new Battlefield(firstMonastery, secondMonastery, start, (end + start) / 2);
        leftHalf.fork();

        Battlefield rightHalf = new Battlefield(firstMonastery, secondMonastery, (end + start) / 2 + 1, end);
        rightHalf.fork();

        Monk leftHalfWinner = leftHalf.join();
        Monk rightHalfWinner = rightHalf.join();
        System.out.println("Fight: " + leftHalfWinner + " and " + rightHalfWinner);

        return Monk.max(leftHalfWinner, rightHalfWinner);
    }
}
