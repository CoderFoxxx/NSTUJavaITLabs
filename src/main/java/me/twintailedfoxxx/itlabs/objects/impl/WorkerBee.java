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
    @Override
    public int getSpawnSeconds() {
        return 2;
    }

    @Override
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/worker_bee.png")));
    }
}
