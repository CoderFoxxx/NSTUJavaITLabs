package me.twintailedfoxxx.itlabs.objects;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import me.twintailedfoxxx.itlabs.AppController;
import me.twintailedfoxxx.itlabs.MainApplication;
import me.twintailedfoxxx.itlabs.objects.impl.QueenBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Habitat
{
    /** Контейнер-корень **/
    private final BorderPane root;

    /** Поле симуляции &ndash; поле в котором будут появляться пчёлы **/
    private final Pane simulationField;

    /** Список созданных пчёл **/
    private final List<Bee> bees;

    /** Ширина поля **/
    private double width;

    /** Высота поля **/
    private double height;

    /** Состояние симуляции **/
    private boolean simulationRunning;

    /** Видимость текста времени симуляции **/
    private boolean isSimulationTimeVisible;

    /** Время начала симуляции **/
    private long start;

    /**
     * Конструктор окружения
     * @param root корневой контейнер <code>Pane</code>
     * @param width ширина поля
     * @param height высота поля
     */
    public Habitat(@NotNull BorderPane root, double width, double height) {
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
            if(bee != null && TimeUnit.MILLISECONDS.toSeconds(elapsed) % bee.getSpawnDelay() == 0L) {
                placeBee(bee);
            }

            updateTime(elapsed);
            AppController.updateDialogBoxText();
        }
    }

    /**
     * Метод, начинающий симуляцию
     */
    public void startSimulation() {
        try {
            if (setSimulationValues()) {
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
                toggleSimulationButtons();
                toggleFields();
            }
        } catch (IllegalArgumentException ex) {
            String errorFieldId = ex.getMessage().split(":")[1].replace(" ", "");
            TextField errorField = (TextField) root.getLeft().lookup("#" + errorFieldId);
            AppController.showInputError(errorField.getPromptText());
            errorField.setText(String.valueOf(errorFieldId.toLowerCase().contains("queen") ? QueenBee.getDelay() :
                    WorkerBee.getDelay()));
        }
    }

    /**
     * Метод, заканчивающий симуляцию
     */
    public void stopSimulation() {
        CheckBox box = (CheckBox) root.getLeft().lookup("#showStatsChkBox");
        ButtonType type = ButtonType.OK;

        if (box.isSelected()) {
            type = AppController.showStatsDialog();
        }

        if (simulationRunning && type == ButtonType.OK) {
            simulationRunning = false;
            MainApplication.instance.timer.cancel();
            bees.clear();
            simulationField.getChildren().removeIf(x -> x instanceof ImageView);
            toggleSimulationButtons();
            toggleFields();
        }
    }

    public BorderPane getRoot() {
        return root;
    }

    public Pane getSimulationField() {
        return simulationField;
    }

    /**
     * Ширина поля
     * @return вещественное число &ndash; ширина поля
     */
    public double getWidth() {
        return width;
    }

    /**
     * Высота поля
     * @return вещественное число &ndash; высота поля
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
        Text t = (Text)root.getLeft().lookup("#hintText");
        t.setVisible(visibility);
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
     * Генерация случайной пчелы
     * @return случайно сгенерированный элемент пчелы
     */
    @Nullable
    private Bee generateRandomBee() {
        double p = MainApplication.instance.random.nextDouble();
        int queenBees = bees.stream().filter(x -> x instanceof QueenBee).toArray().length;

        if ((double) queenBees / bees.size() < QueenBee.getThreshold()) {
            return new QueenBee();
        } else if(p <= WorkerBee.getChance()) {
            return new WorkerBee();
        }

        return null;
    }

    private void updateTime(long elapsed) {
        long elapsedSeconds = TimeUnit.MILLISECONDS.toSeconds(elapsed);
        Text t = (Text) root.getLeft().lookup("#hintText");
        t.setText(String.format("Simulation time: %02d:%02d", (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
    }

    /**
     * Разместить пчелу <code>Bee</code> на сцену.
     * @param bee размещаемая пчела.
     */
    private void placeBee(Bee bee) {
        bees.add(bee);

        ImageView view;
        if (bee instanceof QueenBee) {
            view = new ImageView(QueenBee.IMAGE);
        } else {
            view = new ImageView(WorkerBee.IMAGE);
        }

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
                "\nDog bees spawned: " + bees.stream().filter(x -> x instanceof QueenBee).toList().size() +
                String.format("\nSimulation time: %02d:%02d", (elapsedSeconds % 3600) / 60, elapsedSeconds % 60);
    }

    private void toggleSimulationButtons() {
        Button startBtn = (Button) root.lookup("#beginSimBtn");
        Button endBtn = (Button) root.lookup("#endSimBtn");
        MenuBar menuBar = (MenuBar) root.lookup("#menuBar");
        Menu menu = menuBar.getMenus().get(0);

        startBtn.setDisable(simulationRunning);
        endBtn.setDisable(!simulationRunning);
        menu.getItems().stream().filter(x -> x.getId().equalsIgnoreCase("startSimMenuItem")).findAny()
                .ifPresent(x -> x.setDisable(simulationRunning));
        menu.getItems().stream().filter(x -> x.getId().equalsIgnoreCase("stopSimMenuItem")).findAny()
                .ifPresent(x -> x.setDisable(!simulationRunning));
    }

    @SuppressWarnings("unchecked")
    private void toggleFields() {
        TextField workerIntervalField = (TextField) root.getLeft().lookup("#workerBeeIntervalField");
        TextField queenBeeIntervalField = (TextField) root.getLeft().lookup("#queenBeeIntervalField");
        ComboBox<Double> workerBeePossibility = (ComboBox<Double>) root.getLeft()
                .lookup("#workerBeeSpawnPossibilityCmbBox");
        ComboBox<Double> queenBeePercent = (ComboBox<Double>) root.getLeft().lookup("#queenBeePercentCmbBox");

        workerIntervalField.setDisable(simulationRunning);
        queenBeeIntervalField.setDisable(simulationRunning);
        workerBeePossibility.setDisable(simulationRunning);
        queenBeePercent.setDisable(simulationRunning);
    }

    private boolean setSimulationValues() throws IllegalArgumentException {
        TextField errorField = null;
        try {
            TextField workerIntervalField = (TextField) root.getLeft().lookup("#workerBeeIntervalField");
            TextField queenBeeIntervalField = (TextField) root.getLeft().lookup("#queenBeeIntervalField");

            errorField = workerIntervalField;
            int workerBeeInterval = Integer.parseInt(workerIntervalField.getText());
            errorField = queenBeeIntervalField;
            int queenBeeInterval = Integer.parseInt(queenBeeIntervalField.getText());

            errorField = workerIntervalField;
            if (workerBeeInterval > 0) {
                WorkerBee.setDelay(workerBeeInterval);
            } else {
                throw new IllegalArgumentException("Interval must be a positive number: " + errorField.getId());
            }

            errorField = queenBeeIntervalField;
            if (queenBeeInterval > 0) {
                QueenBee.setDelay(queenBeeInterval);
            } else {
                throw new IllegalArgumentException("Interval must be a positive number: " + errorField.getId());
            }

            return true;
        } catch (NumberFormatException ex) {
            assert errorField != null;
            AppController.showInputError(errorField.getPromptText());
            errorField.setText(String.valueOf(errorField.getId().toLowerCase().contains("queen") ? QueenBee.getDelay() :
                    WorkerBee.getDelay()));
            return false;
        }
    }
}