package me.twintailedfoxxx.itlabs.objects.impl;

import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.Bee;
import me.twintailedfoxxx.itlabs.objects.IBehaviour;

public class WorkerBee extends Bee implements IBehaviour {
    @Override
    public int getSpawnSeconds() {
        return 5;
    }

    @Override
    public boolean canSpawn() {
        return MainApplication.instance.random.nextDouble() <= 0.9;
    }
}
