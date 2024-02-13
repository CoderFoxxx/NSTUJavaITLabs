package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Трутень
 */
public class DogBee extends Bee implements IBehaviour {
    private static int seconds = 5;
    private static double threshold = 0.2;

    public DogBee() {}

    public DogBee(int seconds) {
        DogBee.seconds = seconds;
    }

    @Override
    public int getSpawnSeconds() {
        return seconds;
    }

    @Override
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dog_bee.png")));
    }

    public static double getThreshold() {
        return threshold;
    }

    public static void setThreshold(double threshold) {
        DogBee.threshold = threshold;
    }

    public static void setSeconds(int seconds) {
        DogBee.seconds = seconds;
    }
}
