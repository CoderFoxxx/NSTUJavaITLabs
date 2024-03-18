package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Рабочая пчела
 */
public class WorkerBee extends Bee implements IBehaviour {
    /**
     * Задержка появления рабочей пчелы
     **/
    private static int delay = 2;

    /** Шанс появления рабочей пчелы **/
    private static double chance = 0.9;

    /**
     * Картинка рабочей пчелы
     **/
    public static final Image IMAGE = new Image(Objects.requireNonNull(WorkerBee.class.getResourceAsStream("/worker_bee.png")));

    public WorkerBee() {
        setSpawnDelay(delay);
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
}
