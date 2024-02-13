package me.twintailedfoxxx.itlabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.twintailedfoxxx.itlabs.objects.Habitat;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class MainApplication extends Application {
    public static MainApplication instance;
    public Habitat habitat;
    public Random random;
    public Timer timer;
    public long elapsed;

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("app-view.fxml")));
        Scene scene;

        instance = this;
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
    }

    public static void main(String[] args) {
        launch();
    }
}