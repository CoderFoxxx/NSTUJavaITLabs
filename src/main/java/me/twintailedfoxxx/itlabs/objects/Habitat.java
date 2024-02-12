package me.twintailedfoxxx.itlabs.objects;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
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
    private final BorderPane root;
    private final Text beesSpawnedText;
    private final Text workerBeesSpawnedText;
    private final Text dogBeesSpawnedText;
    private final Text simulationText;
    private double width;
    private double height;
    private final List<Bee> bees;
    private long startTime = 0L;
    private boolean simulationRunning;

    public Habitat(double width, double height) {
        this.width = width;
        this.height = height;
        this.root = new BorderPane();
        bees = new ArrayList<>();

        beesSpawnedText = new Text("Bees spawned: ");
        beesSpawnedText.setFont(Font.font("Comic Sans MS", 30));
        beesSpawnedText.setStroke(Color.RED);
        beesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        beesSpawnedText.setY(40);

        workerBeesSpawnedText = new Text("Worker Bees spawned: ");
        workerBeesSpawnedText.setFont(Font.font("Arial", 20));
        workerBeesSpawnedText.setStroke(Color.DEEPSKYBLUE);
        workerBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        workerBeesSpawnedText.setY(beesSpawnedText.getY() + 15);

        dogBeesSpawnedText = new Text("Dog Bees spawned: ");
        dogBeesSpawnedText.setFont(Font.font("Courier New", 20));
        dogBeesSpawnedText.setStroke(Color.VIOLET);
        dogBeesSpawnedText.setTextAlignment(TextAlignment.LEFT);
        dogBeesSpawnedText.setY(workerBeesSpawnedText.getY() + 15);

        simulationText = new Text("Simulation time: ");
        simulationText.setFont(Font.font("Times New Roman", 20));
        simulationText.setStroke(Color.DARKGREY);
        simulationText.setTextAlignment(TextAlignment.LEFT);
        simulationText.setY(dogBeesSpawnedText.getY() + 15);

        root.getChildren().add(beesSpawnedText);
        root.getChildren().add(workerBeesSpawnedText);
        root.getChildren().add(dogBeesSpawnedText);
        root.getChildren().add(simulationText);
    }

    public void update(long elapsed) {
        while(simulationRunning) {
            Bee bee = generateRandomBee();
            if(bee.canSpawn() && TimeUnit.MILLISECONDS.toSeconds(elapsed) / (long)bee.getSpawnSeconds() == 0L) {
                bees.add(bee);
                // TODO: place smth (square or image of bee)
            }

            elapsed = System.currentTimeMillis() - startTime;
            updateText(elapsed);
        }
    }

    public void startSimulation() {
        simulationRunning = true;
        startTime = System.currentTimeMillis();
        setTextVisibility(true);
    }

    public void stopSimulation() {
        bees.clear();
        simulationRunning = false;
        setTextVisibility(true);
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<Bee> getSpawnedBees() {
        return bees;
    }

    public boolean isSimulationRunning() {
        return simulationRunning;
    }

    public BorderPane getRoot() {
        return root;
    }

    public void setTextVisibility(boolean visibility) {
        for(Node text : root.getChildren().stream().filter(x -> x instanceof Text).toList()) {
            text.setVisible(visibility);
        }
    }

    private void updateText(long elapsed) {
        Duration duration = Duration.ofMillis(elapsed);
        long elapsedSeconds = duration.getSeconds();
        beesSpawnedText.setText("Bees spawned: " + bees.size());
        workerBeesSpawnedText.setText("Worker bees spawned: " + bees.stream().filter(x -> x instanceof WorkerBee).toList().size());
        dogBeesSpawnedText.setText("Dog bees spawned: " + bees.stream().filter(x -> x instanceof DogBee).toList().size());
        simulationText.setText(String.format("Simulation time: %02d:%02d", (elapsedSeconds % 3600) / 60, elapsedSeconds % 60));
    }

    private Bee generateRandomBee() {
        double p = MainApplication.instance.random.nextDouble();
        if(p < 0.5) {
            return new DogBee();
        } else {
            return new WorkerBee();
        }
    }
}
