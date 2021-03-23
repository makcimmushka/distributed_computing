import java.util.Random;

public class Monk {
    private final String monastery;
    public final int energy;

    public Monk(String monastery) {
        this.monastery = monastery;
        this.energy = new Random().nextInt(100);
    }

    public static Monk max(Monk first, Monk second) {
        return first.energy > second.energy
                ? first
                : second;
    }
}
