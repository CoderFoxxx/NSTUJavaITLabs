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
    private long start;

    @Override
    public void start(Stage stage) {
        //FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene;
        BorderPane root = new BorderPane();

        instance = this;
        random = new Random();
        habitat = new Habitat(root, 800, 600);
        scene = new Scene(root, habitat.getWidth(), habitat.getHeight());
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case B:
                    if(!habitat.isSimulationRunning()) {
                        start = System.currentTimeMillis();
                        timer = new Timer();
                        habitat.startSimulation();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                habitat.update(System.currentTimeMillis() - start);
                            }
                        }, 0, 1000);
                    }
                    break;
                case E:
                    if(habitat.isSimulationRunning()) {
                        habitat.stopSimulation();
                        timer.cancel();
                    }
                    break;
                case T:
                    habitat.setSimulationTimeVisibility(!habitat.isSimulationTimeVisible());
                    break;
            }
        });

        stage.setTitle("Bee Simulator");
        stage.widthProperty().addListener((obs, oldVal, newVal) -> habitat.setWidth(newVal.doubleValue()));
        stage.heightProperty().addListener((obs, oldVal, newVal) -> habitat.setHeight(newVal.doubleValue()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}