package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Рабочая пчела
 */
public class WorkerBee extends Bee implements IBehaviour {
    private static int delay = 2;
    private static double chance = 0.9;

    public WorkerBee() {
        setSpawnDelay(delay);
        setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/worker_bee.png"))));
    }

    public static void setDelay(int delay) {
        WorkerBee.delay = delay;
    }

    public static void setChance(double chance) {
        WorkerBee.chance = chance;
    }

    public static double getChance() {
        return chance;
    }
}
