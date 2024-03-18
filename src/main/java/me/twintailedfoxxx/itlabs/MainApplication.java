package me.twintailedfoxxx.itlabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.twintailedfoxxx.itlabs.objects.Habitat;
import me.twintailedfoxxx.itlabs.objects.impl.QueenBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class MainApplication extends Application {
    public static MainApplication instance;
    public Habitat habitat;
    public Random random;
    public Timer timer;
    public AliveBeesDialog aliveBees;
    public long elapsed;

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("app-view.fxml")));
        Scene scene;

        instance = this;
        this.stage = stage;
        random = new Random();
        habitat = new Habitat(root, 800, 600);
        scene = new Scene(root, habitat.getWidth(), habitat.getHeight());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B:
                    if(!habitat.isSimulationRunning()) {
                        habitat.startSimulation();
                    }
                    break;
                case E:
                    if(habitat.isSimulationRunning()) {
                        habitat.stopSimulation();
                    }
                    break;
                case T:
                    habitat.setSimulationTimeVisibility(!habitat.isSimulationTimeVisible());
                    break;
            }
        });

        stage.setTitle("Bee Simulator");
        stage.widthProperty().addListener((obs, oldVal, newVal) -> habitat.setWidth(habitat.getSimulationField().getWidth()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> habitat.setHeight(habitat.getSimulationField().getHeight()));
        stage.setScene(scene);
        stage.show();
        setDefaultValues();
    }

    public static void main(String[] args) {
        launch();
    }

    @SuppressWarnings("unchecked")
    private void setDefaultValues() {
        ((TextField) habitat.getRoot().getLeft().lookup("#workerBeeIntervalField")).setText("2");
        ((TextField) habitat.getRoot().getLeft().lookup("#queenBeeIntervalField")).setText("5");
        ((TextField) habitat.getRoot().getLeft().lookup("#workerBeeLifetimeField")).setText("10");
        ((TextField) habitat.getRoot().getLeft().lookup("#queenBeeLifetimeField")).setText("20");

        ComboBox<Double> workerBeePossibilityBox = (ComboBox<Double>) habitat.getRoot().getLeft().lookup("#workerBeeSpawnPossibilityCmbBox");
        ComboBox<Double> queenBeePercentBox = (ComboBox<Double>) habitat.getRoot().getLeft().lookup("#queenBeePercentCmbBox");
        workerBeePossibilityBox.getSelectionModel().select(workerBeePossibilityBox.getItems().indexOf(WorkerBee.getChance()));
        queenBeePercentBox.getSelectionModel().select(queenBeePercentBox.getItems().indexOf(QueenBee.getThreshold()));
    }

    public Stage getStage() {
        return stage;
    }
}