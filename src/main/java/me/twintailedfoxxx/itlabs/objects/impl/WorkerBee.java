package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Рабочая пчела
 */
public class WorkerBee extends Bee implements IBehaviour {
    private static int seconds = 2;
    private static double chance = 0.5;

    public WorkerBee() {}

    public WorkerBee(int seconds) {
        WorkerBee.seconds = seconds;
    }

    @Override
    public int getSpawnSeconds() {
        return seconds;
    }

    @Override
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/worker_bee.png")));
    }

    public static void setSeconds(int seconds) {
        WorkerBee.seconds = seconds;
    }

    public static void setChance(double chance) {
        WorkerBee.chance = chance;
    }

    public static double getChance() {
        return chance;
    }
}
