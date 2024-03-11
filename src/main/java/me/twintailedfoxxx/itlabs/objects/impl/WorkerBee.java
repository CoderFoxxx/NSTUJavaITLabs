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
    private static int lifeTime = 10;

    public WorkerBee() {
        setId(0);
        setSpawnDelay(delay);
        setLifeTime(lifeTime);
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

    public static int getDelay() {
        return delay;
    }

    public static void setLifetime(int lifeTime) {
        WorkerBee.lifeTime = lifeTime;
    }

    @Override
    public String toString() {
        return "Worker" + super.toString();
    }
}
