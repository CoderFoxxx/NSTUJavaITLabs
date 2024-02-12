package me.twintailedfoxxx.itlabs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.twintailedfoxxx.itlabs.objects.Habitat;

import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplication extends Application {
    public static MainApplication instance;
    public Random random;
    public Timer timer;
    public Habitat habitat;
    private long elapsed = 0;

    @Override
    public void start(Stage stage) {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        instance = this;
        random = new Random();
        habitat = new Habitat(800, 600);
        Scene scene = new Scene(habitat.getRoot(), habitat.getWidth(), habitat.getHeight());
        stage.setTitle("Bee Simulator");
        stage.setScene(scene);
        stage.show();
        habitat.startSimulation();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                habitat.update(elapsed);
            }
        }, 0, 1000);
    }

    public static void main(String[] args) {
        launch();
    }
}