package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Трутень
 */
public class QueenBee extends Bee implements IBehaviour {
    private static int delay = 5;
    private static double threshold = 0.2;
    private static int lifeTime = 20;

    public QueenBee() {
        setId(0);
        setSpawnDelay(delay);
        setLifeTime(lifeTime);
        setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/queen_bee.png"))));
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

    public static void setLifetime(int lifeTime) {
        QueenBee.lifeTime = lifeTime;
    }

    public static int getDelay() {
        return delay;
    }

    @Override
    public String toString() {
        return "Queen" + super.toString();
    }
}
