package me.twintailedfoxxx.itlabs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import me.twintailedfoxxx.itlabs.objects.impl.QueenBee;
import me.twintailedfoxxx.itlabs.objects.impl.WorkerBee;

import java.util.Optional;

@SuppressWarnings("unused")
public class AppView {
    // Контейнер-корень
    @FXML
    private BorderPane root;

    @FXML
    private VBox controlPanel;

    @FXML
    private Pane simulationField;

    // Текст-подсказка
    @FXML
    private Text hintText;

    @FXML
    private Button beginSimBtn;

    @FXML
    private Button endSimBtn;

    @FXML
    private Button showStatsBtn;

    @FXML
    private Button showTimeBtn;

    @FXML
    private Button hideTimeBtn;

    @FXML
    private TextField workerBeeIntervalField;

    @FXML
    private TextField dogBeeIntervalField;

    @FXML
    private ComboBox<Double> workerBeeSpawnPossibilityCmbBox;

    @FXML
    private ComboBox<Double> dogBeePercentCmbBox;

    @FXML
    private void startSimBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.startSimulation();
        beginSimBtn.setDisable(true);
        endSimBtn.setDisable(false);
    }

    @FXML
    private void endSimBtnClick(MouseEvent event) {
        ButtonType type = showStatsDialog();
        if(MainApplication.instance.habitat.isSimulationRunning() && type == ButtonType.OK) {
            MainApplication.instance.habitat.stopSimulation();
            beginSimBtn.setDisable(false);
            endSimBtn.setDisable(true);
        }
    }

    @FXML
    private void statsBtnClick(MouseEvent event) {
        showStatsDialog();
    }

    @FXML
    private void showTimeBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.setSimulationTimeVisibility(true);
    }

    @FXML
    private void hideTimeBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.setSimulationTimeVisibility(false);
    }

    @FXML
    private void workerBeeIntervalFieldAction(ActionEvent event) {
        try {
            WorkerBee.setDelay(Integer.parseInt(workerBeeIntervalField.getText()));
        } catch (NumberFormatException ex) {
            showError("Вы ввели некорректные данные. Введите корректные данные в поле (целое число) и попробуйте ещё раз.");
        }
    }

    @FXML
    private void dogBeeIntervalFieldAction(ActionEvent event) {
        try {
            QueenBee.setDelay(Integer.parseInt(dogBeeIntervalField.getText()));
        } catch (NumberFormatException ex) {
            showError("Вы ввели некорректные данные. Введите корректные данные в поле (целое число) и попробуйте ещё раз.");
        }
    }

    @FXML
    private void workerBeeSpawnPossibilityCmbBoxAction(ActionEvent event) {
        WorkerBee.setChance(workerBeeSpawnPossibilityCmbBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void dogBeePercentCmbBoxAction(ActionEvent event) {
        QueenBee.setThreshold(dogBeePercentCmbBox.getSelectionModel().getSelectedItem());
    }

    private ButtonType showStatsDialog() {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setHeaderText("Статистика");
        dialog.setContentText(MainApplication.instance.habitat
                .getStatisticString(MainApplication.instance.elapsed));
        Optional<ButtonType> buttonTypeOptional = dialog.showAndWait();

        return buttonTypeOptional.orElse(null);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
    }
}