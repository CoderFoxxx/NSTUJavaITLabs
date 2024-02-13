package me.twintailedfoxxx.itlabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import me.twintailedfoxxx.itlabs.objects.Habitat;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplication extends Application {
    public static MainApplication instance;
    public Random random;
    public Timer timer;
    public long elapsed;
    public Habitat habitat;
    private long start;

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
                    handleSimulationStart();
                    break;
                case E:
                    handleSimulationStop();
                    break;
            }
        });

        stage.setTitle("Bee Simulator");
        stage.widthProperty().addListener((obs, oldVal, newVal) -> habitat.setWidth(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> habitat.setHeight(newVal.doubleValue()));
        stage.setScene(scene);
        stage.show();
    }

    public void handleSimulationStart() {
        if(!habitat.isSimulationRunning()) {
            start = System.currentTimeMillis();
            timer = new Timer();
            habitat.startSimulation();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    elapsed = System.currentTimeMillis() - start;
                    habitat.update(elapsed);
                }
            }, 0, 1000);
        }
    }

    public void handleSimulationStop() {
        if(habitat.isSimulationRunning()) {
            habitat.stopSimulation();
            timer.cancel();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}