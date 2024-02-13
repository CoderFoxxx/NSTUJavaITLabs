package me.twintailedfoxxx.itlabs.objects.impl;

import javafx.scene.image.Image;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

import java.util.Objects;

/**
 * Трутень
 */
public class DogBee extends Bee implements IBehaviour {
    @Override
    public int getSpawnSeconds() {
        return 5;
    }

    @Override
    public Image getImage() {
        return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/dog_bee.png")));
    }
}
