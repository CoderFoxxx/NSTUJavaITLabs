package me.twintailedfoxxx.itlabs.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.scene.image.Image;

/**
 * Пчела
 */
public abstract class Bee
{
    /**
     * Уникальный идентифиатор объекта пчелы
     */
    private int id;

    /**
     * Период между появлениями новых пчел в секундах
     */
    private int spawnDelay;

    /**
     * Время жизни пчелы в секундах
     */
    private int lifeTime;

    /**
     * Время рождения пчелы в миллисекундах
     */
    private long birthTime;

    /** Картинка пчелы */
    private Image image;

    /**
     * Уникальный идентификатор пчелы
     *
     * @return уникальный идентифиатор объекта пчелы
     */
    public int getId() {
        return id;
    }

    /**
     * Установка нового уникального идентификатора пчелы
     *
     * @param id новый идентификатор
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Период между появлениями пчел
     * @return период между появлениями новых пчел в секундах
     */
    public int getSpawnDelay() {
        return spawnDelay;
    }

    /**
     * Установить новый период между появлениями пчел
     * @param spawnDelay новый период между появлениями пчел в секундах
     */
    public void setSpawnDelay(int spawnDelay) {
        this.spawnDelay = spawnDelay;
    }

    /**
     * Время жизни пчелы
     *
     * @return время жизни пчелы в секундах
     */
    public int getLifeTime() {
        return lifeTime;
    }

    /**
     * Установить время жизни пчелы
     *
     * @param lifeTime новое время жизни пчелы в секундах
     */
    public void setLifeTime(int lifeTime) {
        this.lifeTime = lifeTime;
    }

    /**
     * Время появления пчелы
     *
     * @return время появления пчелы в миллисекундах
     */
    public long getBirthTime() {
        return birthTime;
    }

    /**
     * Установить время появления пчелы
     *
     * @param birthTime время появления пчелы в миллисекундах
     */
    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    /**
     * Картинка пчелы
     * @return объект картинки пчелы
     */
    public Image getImage() {
        return image;
    }

    /**
     * Установить новую картинку для пчелы
     * @param image новая картинка для пчелы
     */
    public void setImage(Image image) {
        this.image = image;
    }

    public SimpleIntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    public SimpleIntegerProperty spawnDelayProperty() {
        return new SimpleIntegerProperty(spawnDelay);
    }

    public SimpleIntegerProperty lifeTimeProperty() {
        return new SimpleIntegerProperty(lifeTime);
    }

    public SimpleLongProperty birthTimeProperty() {
        return new SimpleLongProperty(birthTime);
    }

    @Override
    public String toString() {
        return "Bee (#" + getId() + ")";
    }
}