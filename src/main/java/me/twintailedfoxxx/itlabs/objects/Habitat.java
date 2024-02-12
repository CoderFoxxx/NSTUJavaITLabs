package me.twintailedfoxxx.itlabs.objects;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
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

    //Контейнер, содержащий статистику
    private final Pane statsPane;

    // Текст-подсказка
    private final Text hintText;

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

    // Состояние симуляции
    private boolean simulationRunning;

    // Видимость текста времени симуляции
    private boolean isSimulationTimeVisible;


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
        this.bees = new ArrayList<>();
        this.statsPane = new Pane();

        hintText = new Text("Press B to begin the simulation, T to show simulation time.");
        hintText.setFont(Font.font("Roboto Regular", 20));
        hintText.setTextAlignment(TextAlignment.CENTER);

        beesSpawnedText = new Text("Bees spawned:");
        beesSpawnedText.setFont(Font.font("Comic Sans MS", 30));
        beesSpawnedText.setStroke(Color.RED);
        beesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        beesSpawnedText.setY(40);

        workerBeesSpawnedText = new Text("Worker Bees spawned:");
        workerBeesSpawnedText.setFont(Font.font("Arial", 20));
        workerBeesSpawnedText.setStroke(Color.DEEPSKYBLUE);
        workerBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        workerBeesSpawnedText.setY(beesSpawnedText.getY() + 15);

        dogBeesSpawnedText = new Text("Dog Bees spawned:");
        dogBeesSpawnedText.setFont(Font.font("Courier New", 20));
        dogBeesSpawnedText.setStroke(Color.VIOLET);
        dogBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        dogBeesSpawnedText.setY(workerBeesSpawnedText.getY() + 15);

        simulationText = new Text("Simulation time:");
        simulationText.setId("stats_simTime");
        simulationText.setFont(Font.font("Times New Roman", 20));
        simulationText.setStroke(Color.DARKGREY);
        simulationText.setTextAlignment(TextAlignment.CENTER);
        simulationText.setY(dogBeesSpawnedText.getY() + 15);

        hintText.setY(simulationText.getY() + 15);

        statsPane.getChildren().addAll(beesSpawnedText, workerBeesSpawnedText, dogBeesSpawnedText);
        root.getChildren().addAll(simulationText, hintText);

        simulationText.setVisible(false);
        statsPane.setVisible(false);
    }

    /**
     * Этот метод вызывается раз в секунду во время симуляции.
     * @param elapsed затраченное время
     */
    public void update(long elapsed) {
        if(simulationRunning) {
            Bee bee = generateRandomBee();
            if(bee != null && TimeUnit.MILLISECONDS.toSeconds(elapsed) % bee.getSpawnSeconds() == 0L) {
                placeBee(bee);
            }
            updateText(elapsed);
        }
    }

    /**
     * Метод, начинающий симуляцию
     */
    public void startSimulation() {
        simulationRunning = true;
        hintText.setText("Press E to stop simulation, T to show simulation time.");
    }

    /**
     * Метод, заканчивающий симуляцию
     */
    public void stopSimulation() {
        simulationRunning = false;
        statsPane.setVisible(true);
        bees.clear();
        root.getChildren().removeIf(x -> x instanceof ImageView);
        hintText.setText("Press B to start simulation again, T to show simulation time.");
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
     * Установка видимости текста времени симуляции в сцене
     * @param visibility <code>true</code> &ndash; показать текст<br>
     *                   <code>false</code> &ndash; скрыть текст
     */
    public void setSimulationTimeVisibility(boolean visibility) {
        isSimulationTimeVisible = visibility;
        for(Node text : root.getChildren().filtered(x -> x instanceof Text && x.getId() != null && x.getId().equalsIgnoreCase("stats_simTime"))) {
            text.setVisible(visibility);
        }
    }

    /**
     * Видимость текста времени симуляции в сцене
     * @return <code>true</code> &ndash; время видно на сцене<br>
     * <code>false</code> &ndash; время не видно на сцене
     */
    public boolean isSimulationTimeVisible() {
        return isSimulationTimeVisible;
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
        int dogBees = bees.stream().filter(x -> x instanceof DogBee).toList().size();

        if((double)dogBees / bees.size() <= 0.2) {
            return new DogBee();
        } else if(p <= 0.9) {
            return new WorkerBee();
        }

        return null;
    }

    /**
     * Разместить пчелу <code>Bee</code> на сцену.
     * @param bee размещаемая пчела.
     */
    private void placeBee(Bee bee) {
        bees.add(bee);

        ImageView view = new ImageView(bee.getImage());
        view.setFitWidth(30);
        view.setFitHeight(30);

        double x = MainApplication.instance.random.nextDouble() * (width - view.getFitWidth());
        double y = MainApplication.instance.random.nextDouble() * (height - view.getFitHeight());
        view.setX(x);
        view.setY(y);

        Platform.runLater(() -> root.getChildren().add(view));
    }
}
