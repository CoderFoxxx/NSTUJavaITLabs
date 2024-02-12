package me.twintailedfoxxx.itlabs.objects.impl;

import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

/**
 * Трутень
 */
public class DogBee extends Bee implements IBehaviour {
    //Количество трутней
    private static int count = 0;

    public DogBee() {
        if(canSpawn()) {
            count++;
        }
    }

    @Override
    public int getSpawnSeconds() {
        return 15;
    }

    @Override
    public boolean canSpawn() {
        return MainApplication.instance.habitat.getSpawnedBees().size() / count * 100 < 2;
    }
}
