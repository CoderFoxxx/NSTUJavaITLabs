package me.twintailedfoxxx.itlabs.objects;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.impl.DogBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Habitat
{
    // Контейнер-корень
    private final BorderPane root;

    private final Pane simulationField;

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
    public Habitat(BorderPane root, double width, double height) {
        this.width = width;
        this.height = height;
        this.root = root;
        this.bees = new ArrayList<>();
        this.simulationField = (Pane)root.getRight();
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
        }
    }

    /**
     * Метод, начинающий симуляцию
     */
    public void startSimulation() {
        simulationRunning = true;
    }

    /**
     * Метод, заканчивающий симуляцию
     */
    public void stopSimulation() {
        simulationRunning = false;
        bees.clear();
        simulationField.getChildren().removeIf(x -> x instanceof ImageView);
        //hintText.setText("Press B to start simulation again, T to show simulation time.");
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
    /*public void setSimulationTimeVisibility(boolean visibility) {
        isSimulationTimeVisible = visibility;
        for(Node text : simulationField.getChildren().filtered(x -> x instanceof Text && x.getId() != null &&
                x.getId().equalsIgnoreCase("stats_simTime"))) {
            text.setVisible(visibility);
        }
    }*/

    /**
     * Видимость текста времени симуляции в сцене
     * @return <code>true</code> &ndash; время видно на сцене<br>
     * <code>false</code> &ndash; время не видно на сцене
     */
    /*public boolean isSimulationTimeVisible() {
        return isSimulationTimeVisible;
    }*/

    /**
     * Генерация случайной пчелы
     * @return случайно сгенерированный элемент пчелы
     */
    private Bee generateRandomBee() {
        double p = MainApplication.instance.random.nextDouble();
        int dogBees = bees.stream().filter(x -> x instanceof DogBee).toArray().length;

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

        Platform.runLater(() -> simulationField.getChildren().add(view));
    }

    public String getStatisticString(long elapsed) {
        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
        return "Bees spawned: " + bees.size() +
                "\nWorker bees spawned: " + bees.stream().filter(x -> x instanceof WorkerBee).toList().size() +
                "\nDog bees spawned: " + bees.stream().filter(x -> x instanceof DogBee).toList().size() +
                String.format("\nSimulation time: %02d:%02d", (elapsedSeconds % 3600) / 60, elapsedSeconds % 60);
    }
}
