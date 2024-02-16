package me.twintailedfoxxx.itlabs.objects;

import javafx.scene.image.Image;

/**
 * Пчела
 */
public abstract class Bee
{
    private int spawnDelay;
    private Image image;

    /**
     * Время, через которое появится пчела
     * @return количество секунд, через которое появится пчела
     */
    public int getSpawnDelay() {
        return spawnDelay;
    }

    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}