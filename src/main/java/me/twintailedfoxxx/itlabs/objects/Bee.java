package me.twintailedfoxxx.itlabs.objects;

/**
 * Пчела
 */
public abstract class Bee
{
    private int spawnDelay;

    /**
     * Время, через которое появится пчела
     * @return количество секунд, через которое появится пчела
     */
    public int getSpawnDelay() {
        return spawnDelay;
    }

    /**
     * Установить время через которое появится пчела
     *
     * @param spawnDelay количество секунд, через которое появится пчела
     */
    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }
}