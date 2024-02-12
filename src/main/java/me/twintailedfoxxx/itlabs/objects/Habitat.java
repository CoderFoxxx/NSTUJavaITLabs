package me.twintailedfoxxx.itlabs.objects;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.impl.DogBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Habitat
{
    // Контейнер-корень
    private final Pane root;

    // Текст, в котором показывается число созданных пчёл
    private final Text beesSpawnedText;

    // Текст, в котором показывается число рабочих пчёл
    private final Text workerBeesSpawnedText;

    // Текст, в котором показывается число трутней
    private final Text dogBeesSpawnedText;

    // Текст, в котором пишется время, затраченное на симуляцию
    private final Text simulationText;

    // Список созданных пчёл
    private final List<Bee> bees;

    // Ширина поля
    private double width;

    // Высота поля
    private double height;

    // Время начала симуляции
    private long startTime = 0L;

    // Состояние симуляции
    private boolean simulationRunning;


    /**
     * Конструктор окружения
     * @param root корневой контейнер <code>Pane</code>
     * @param width ширина поля
     * @param height высота поля
     */
    public Habitat(Pane root, double width, double height) {
        this.width = width;
        this.height = height;
        this.root = root;
        bees = new ArrayList<>();

        beesSpawnedText = new Text("Bees spawned: ");
        beesSpawnedText.setFont(Font.font("Comic Sans MS", 30));
        beesSpawnedText.setStroke(Color.RED);
        beesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        beesSpawnedText.setY(40);

        workerBeesSpawnedText = new Text("Worker Bees spawned: ");
        workerBeesSpawnedText.setFont(Font.font("Arial", 20));
        workerBeesSpawnedText.setStroke(Color.DEEPSKYBLUE);
        workerBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        workerBeesSpawnedText.setY(beesSpawnedText.getY() + 15);

        dogBeesSpawnedText = new Text("Dog Bees spawned: ");
        dogBeesSpawnedText.setFont(Font.font("Courier New", 20));
        dogBeesSpawnedText.setStroke(Color.VIOLET);
        dogBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        dogBeesSpawnedText.setY(workerBeesSpawnedText.getY() + 15);

        simulationText = new Text("Simulation time: ");
        simulationText.setFont(Font.font("Times New Roman", 20));
        simulationText.setStroke(Color.DARKGREY);
        simulationText.setTextAlignment(TextAlignment.LEFT);
        simulationText.setY(dogBeesSpawnedText.getY() + 15);

        root.getChildren().add(beesSpawnedText);
        root.getChildren().add(workerBeesSpawnedText);
        root.getChildren().add(dogBeesSpawnedText);
        root.getChildren().add(simulationText);
    }

    /**
     * Этот метод вызывается раз в секунду во время симуляции.
     * @param elapsed затраченное время
     */
    public void update(long elapsed) {
        if(simulationRunning) {
            Bee bee = generateRandomBee();
            if(bee.canSpawn() && TimeUnit.MILLISECONDS.toSeconds(elapsed) % (long)bee.getSpawnSeconds() == 0L) {
                bees.add(bee);
                // TODO: place smth (square or image of bee)
            }

            elapsed = System.currentTimeMillis() - startTime;
            updateText(elapsed);
        }
    }

    /**
     * Метод, начинающий симуляцию
     */
    public void startSimulation() {
        simulationRunning = true;
        startTime = System.currentTimeMillis();
        setTextVisibility(true);
    }

    /**
     * Метод, заканчивающий симуляцию
     */
    public void stopSimulation() {
        bees.clear();
        simulationRunning = false;
        setTextVisibility(true);
    }

    /**
     * Ширина поля
     * @return вещественное число - ширина поля
     */
    public double getWidth() {
        return width;
    }

    /**
     * Высота поля
     * @return вещественное число - высота поля
     */
    public double getHeight() {
        return height;
    }

    /**
     * Установка новой ширины поля
     * @param width новая ширина поля
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Установка новой высоты поля
     * @param height новая высота поля
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Список созданных пчёл
     * @return список всех созданных пчёл
     */
    public List<Bee> getSpawnedBees() {
        return bees;
    }

    /**
     * Состояние симуляции
     * @return <code>true</code>, если симуляция запущена<br>
     * <code>false</code>, если симуляция остановлена
     */
    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    /**
     * Корневой контейнер
     * @return элемент корневого контейнера <code>Pane</code>
     */
    public Pane getRoot() {
        return root;
    }

    /**
     * Установка видимости текста в сцене
     * @param visibility <code>true</code> - показать текст<br>
     *                   <code>false</code> - скрыть текст
     */
    public void setTextVisibility(boolean visibility) {
        for(Node text : root.getChildren().stream().filter(x -> x instanceof Text).toList()) {
            text.setVisible(visibility);
        }
    }

    /**
     * Обновление содержания текстовых объектов
     * @param elapsed затраченное время на симуляцию
     */
    private void updateText(long elapsed) {
        Duration duration = Duration.ofMillis(elapsed);
        long elapsedSeconds = duration.getSeconds();
        beesSpawnedText.setText("Bees spawned: " + bees.size());
        workerBeesSpawnedText.setText("Worker bees spawned: " + bees.stream().filter(x -> x instanceof WorkerBee).toList().size());
        dogBeesSpawnedText.setText("Dog bees spawned: " + bees.stream().filter(x -> x instanceof DogBee).toList().size());
        simulationText.setText(String.format("Simulation time: %02d:%02d", (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
    }

    /**
     * Генерация случайной пчелы
     * @return случайно сгенерированный элемент пчелы
     */
    private Bee generateRandomBee() {
        double p = MainApplication.instance.random.nextDouble();
        if(p < 0.5) {
            return new DogBee();
        } else {
            return new WorkerBee();
        }
    }
}
