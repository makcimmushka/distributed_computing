package util;

import entity.Stuff;

import java.time.LocalDateTime;
import java.util.Random;

public class StuffGenerator {
    private static final Random random = new Random();

    private StuffGenerator() {
        throw new IllegalStateException("Utility class");
    }

    public static Stuff generate() {
        return new Stuff(
                String.format("Stuff-%s", LocalDateTime.now()),
                random.nextInt(100)
        );
    }
}
