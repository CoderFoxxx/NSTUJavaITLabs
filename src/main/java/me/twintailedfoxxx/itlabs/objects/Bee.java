package me.twintailedfoxxx.itlabs.objects;

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

    /**
     * Условие появление пчелы
     * @return <code>true</code> - пчела может появиться<br>
     * <code>false</code> - пчела не может появиться
     */
    public abstract boolean canSpawn();
}
