package me.twintailedfoxxx.itlabs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
    private void startSimBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.startSimulation();
        beginSimBtn.setDisable(true);
        endSimBtn.setDisable(false);
    }

    @FXML
    private void endSimBtnClick(MouseEvent event) {
        showStatsDialog();
        MainApplication.instance.habitat.stopSimulation();
        beginSimBtn.setDisable(false);
        endSimBtn.setDisable(true);
    }

    @FXML
    private void statsBtnClick(MouseEvent event) {
        showStatsDialog();
    }

    private void showStatsDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Статистика");
        dialog.setContentText(MainApplication.instance.habitat
                .getStatisticString(MainApplication.instance.elapsed));
        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okBtn, cancelBtn);
        dialog.showAndWait();
    }
}
