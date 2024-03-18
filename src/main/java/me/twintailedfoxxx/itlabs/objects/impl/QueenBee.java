package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Трутень
 */
public class QueenBee extends Bee implements IBehaviour {
    /**
     * Задержка появления трутня
     **/
    private static int delay = 5;

    /** Процент от всех пчел для появления трутня **/
    private static double threshold = 0.2;

    public static final Image IMAGE = new Image(Objects.requireNonNull(QueenBee.class.getResourceAsStream("/queen_bee.png")));

    public QueenBee() {
        setSpawnDelay(delay);
    }

    public static double getThreshold() {
        return threshold;
    }

    public static void setThreshold(double threshold) {
        QueenBee.threshold = threshold;
    }

    public static void setDelay(int delay) {
        QueenBee.delay = delay;
    }

    public static int getDelay() {
        return delay;
    }
}
