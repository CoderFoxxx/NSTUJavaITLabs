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
    private static double chance = 0.9;

    public WorkerBee() {
        setSpawnDelay(2);
        setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/worker_bee.png"))));
    }

    public static double getChance() {
        return chance;
    }

    public static void setChance(double chance) {
        WorkerBee.chance = chance;
    }
}
