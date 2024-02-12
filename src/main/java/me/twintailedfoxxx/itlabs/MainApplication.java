package me.twintailedfoxxx.itlabs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import me.twintailedfoxxx.itlabs.objects.Habitat;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainApplication extends Application {
    public static MainApplication instance;
    public Random random;
    public Timer timer;
    public Habitat habitat;
    private long elapsed;

    @Override
    public void start(Stage stage) {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        BorderPane root = new BorderPane();
        instance = this;
        random = new Random();
        habitat = new Habitat(root, 800, 600);
        elapsed = 0L;
        Scene scene = new Scene(root, habitat.getWidth(), habitat.getHeight());
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