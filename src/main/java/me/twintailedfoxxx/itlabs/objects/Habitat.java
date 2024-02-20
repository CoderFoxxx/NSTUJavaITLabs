package me.twintailedfoxxx.itlabs.objects;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.impl.QueenBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;

import java.time.Duration;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Habitat
{
    // Контейнер-корень
    private final Pane root;

    private final Pane statsPane;

    // Текст-подсказка
    private final Text hintText;

    // Текст, в котором показывается число созданных пчёл
    private final Text beesSpawnedText;

    // Текст, в котором показывается число рабочих пчёл
    private final Text workerBeesSpawnedText;

    // Текст, в котором показывается число трутней
    private final Text queenBeesSpawnedText;

    // Текст, в котором пишется время, затраченное на симуляцию
    private final Text simulationText;

    // Массив пчёл
    private final Bee[] bees;

    // Количество появившихся пчёл
    private int beesSpawned;

    // Ширина поля
    private double width;

    // Высота поля
    private double height;

    // Состояние симуляции
    private boolean simulationRunning;

    // Видимость текста времени симуляции
    private boolean isSimulationTimeVisible;
    private long start;


    /**
     * Конструктор окружения
     * @param root корневой контейнер <code>Pane</code>
     * @param width ширина поля
     * @param height высота поля
     */
    public Habitat(Pane root, double width, double height) {
        this.statsPane = new Pane();
        this.width = width;
        this.height = height;
        this.root = root;
        this.bees = new Bee[1024];

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

        queenBeesSpawnedText = new Text("Queen Bees spawned:");
        queenBeesSpawnedText.setFont(Font.font("Courier New", 20));
        queenBeesSpawnedText.setStroke(Color.VIOLET);
        queenBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        queenBeesSpawnedText.setY(workerBeesSpawnedText.getY() + 15);

        simulationText = new Text("Simulation time:");
        simulationText.setId("stats_simTime");
        simulationText.setFont(Font.font("Times New Roman", 20));
        simulationText.setStroke(Color.DARKGREY);
        simulationText.setTextAlignment(TextAlignment.CENTER);
        simulationText.setY(queenBeesSpawnedText.getY() + 15);

        hintText.setY(simulationText.getY() + 15);

        statsPane.getChildren().addAll(beesSpawnedText, workerBeesSpawnedText, queenBeesSpawnedText);
        root.getChildren().addAll(statsPane, simulationText, hintText);

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
            if(bee != null && TimeUnit.MILLISECONDS.toSeconds(elapsed) % bee.getSpawnDelay() == 0L) {
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
        start = System.currentTimeMillis();
        MainApplication.instance.timer = new Timer();
        MainApplication.instance.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                MainApplication.instance.elapsed = System.currentTimeMillis() - start;
                update(MainApplication.instance.elapsed);
            }
        }, 0, 1000);
        hintText.setText("Press E to stop simulation, T to show simulation time.");
    }

    /**
     * Метод, заканчивающий симуляцию
     */
    public void stopSimulation() {
        simulationRunning = false;
        MainApplication.instance.timer.cancel();
        for(int i = 0; i < beesSpawned; i++) {
            bees[i] = null;
        }
        beesSpawned = 0;
        root.getChildren().removeIf(x -> x instanceof ImageView);
        hintText.setText("Press B to start simulation again, T to show simulation time.");
        statsPane.setVisible(true);
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
     * Массив созданных пчёл
     * @return массив всех созданных пчёл
     */
    public Bee[] getSpawnedBees() {
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
        Text t = (Text)root.lookup("#stats_simTime");
        if(t != null) {
            t.setVisible(visibility);
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
        beesSpawnedText.setText("Bees spawned: " + beesSpawned);
        workerBeesSpawnedText.setText("Worker bees spawned: " + Arrays.stream(bees).filter(x -> x instanceof WorkerBee)
                .toArray().length);
        queenBeesSpawnedText.setText("Queen bees spawned: " + Arrays.stream(bees).filter(x -> x instanceof QueenBee)
                .toArray().length);
        simulationText.setText(String.format("Simulation time: %02d:%02d", (elapsedSeconds % 3600) / 60,
                elapsedSeconds % 60));
    }

    /**
     * Генерация случайной пчелы
     * @return случайно сгенерированный элемент пчелы
     */
    private Bee generateRandomBee() {
        double p = MainApplication.instance.random.nextDouble();
        int queenBees = Arrays.stream(bees).filter(x -> x instanceof QueenBee).toArray().length;

        if ((double) queenBees / beesSpawned < QueenBee.getThreshold()) {
            return new QueenBee();
        } else if(p <= WorkerBee.getChance()) {
            return new WorkerBee();
        }

        return null;
    }

    /**
     * Разместить пчелу <code>Bee</code> на сцену.
     * @param bee размещаемая пчела.
     */
    private void placeBee(Bee bee) {
        bees[beesSpawned] = bee;
        beesSpawned += 1;

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