package me.twintailedfoxxx.itlabs.objects;

import javafx.scene.image.Image;

/**
 * Пчела
 */
public abstract class Bee
{
    /**
     * Время, через которое появится пчела
     * @return количество секунд, через которое появится пчела
     */
    public abstract int getSpawnSeconds();
    public abstract Image getImage();
}
