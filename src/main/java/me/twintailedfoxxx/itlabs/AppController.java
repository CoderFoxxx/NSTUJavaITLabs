package me.twintailedfoxxx.itlabs;

import javafx.application.Platform;
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
public class AppController {
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
    private CheckBox showStatsChkBox;

    @FXML
    private RadioButton showTimeRadioBtn;

    @FXML
    private RadioButton hideTimeRadioBtn;

    @FXML
    private TextField workerBeeIntervalField;

    @FXML
    private TextField queenBeeIntervalField;

    @FXML
    private ComboBox<Double> workerBeeSpawnPossibilityCmbBox;

    @FXML
    private ComboBox<Double> queenBeePercentCmbBox;

    @FXML
    private TextField workerBeeLifetimeField;

    @FXML
    private TextField queenBeeLifetimeField;

    @FXML
    private Button aliveBeesBtn;

    private static Alert statsDialog;

    @FXML
    private void onStartSimBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.startSimulation();
    }

    @FXML
    private void onEndSimBtnClick(MouseEvent event) {
        MainApplication.instance.habitat.stopSimulation();
    }

    @FXML
    private void onShowTimeRadioBtnClick(ActionEvent event) {
        if (hideTimeRadioBtn.isSelected()) {
            hideTimeRadioBtn.setSelected(false);
        }

        MenuBar menuBar = (MenuBar) MainApplication.instance.habitat.getRoot().lookup("#menuBar");
        Menu menu = menuBar.getMenus().get(0);
        menu.getItems().stream().filter(x -> x instanceof CheckMenuItem && x.getId().equalsIgnoreCase("showTimeMenuItem")).findAny()
                .ifPresent(x -> ((CheckMenuItem) x).setSelected(true));

        MainApplication.instance.habitat.setSimulationTimeVisibility(true);
    }

    @FXML
    private void onHideTimeRadioBtnClick(ActionEvent event) {
        if (showTimeRadioBtn.isSelected()) {
            showTimeRadioBtn.setSelected(false);
        }

        MenuBar menuBar = (MenuBar) MainApplication.instance.habitat.getRoot().lookup("#menuBar");
        Menu menu = menuBar.getMenus().get(0);
        menu.getItems().stream().filter(x -> x instanceof CheckMenuItem && x.getId().equalsIgnoreCase("showTimeMenuItem")).findAny()
                .ifPresent(x -> ((CheckMenuItem) x).setSelected(false));

        MainApplication.instance.habitat.setSimulationTimeVisibility(false);
    }

    @FXML
    private void onWorkerBeeIntervalFieldAction(ActionEvent event) {
        try {
            WorkerBee.setDelay(Integer.parseInt(workerBeeIntervalField.getText()));
        } catch (NumberFormatException ex) {
            showInputError(((TextField) event.getSource()).getPromptText());
            workerBeeIntervalField.setText(String.valueOf(WorkerBee.getDelay()));
        }
    }

    @FXML
    private void onQueenBeeIntervalFieldAction(ActionEvent event) {
        try {
            QueenBee.setDelay(Integer.parseInt(queenBeeIntervalField.getText()));
        } catch (NumberFormatException ex) {
            showInputError(((TextField) event.getSource()).getPromptText());
            queenBeeIntervalField.setText(String.valueOf(QueenBee.getDelay()));
        }
    }

    @FXML
    private void onWorkerBeeSpawnPossibilityCmbBoxAction(ActionEvent event) {
        WorkerBee.setChance(workerBeeSpawnPossibilityCmbBox.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void onQueenBeePercentCmbBoxAction(ActionEvent event) {
        QueenBee.setThreshold(queenBeePercentCmbBox.getSelectionModel().getSelectedItem());
    }

    public static ButtonType showStatsDialog() {
        statsDialog = new Alert(Alert.AlertType.CONFIRMATION);
        statsDialog.setHeaderText("Статистика");
        statsDialog.setContentText(MainApplication.instance.habitat.getStatisticString(MainApplication.instance.elapsed));
        Optional<ButtonType> buttonTypeOptional = statsDialog.showAndWait();

        return buttonTypeOptional.orElse(null);
    }

    public static void showInputError(String fieldName) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Вы ввели некорректные данные в поле \"" + fieldName + "\". Введите корректные данные в поле " +
                "(положительное целое число) и попробуйте ещё раз.");
        alert.showAndWait();
    }

    public static void updateDialogBoxText() {
        if (statsDialog != null) {
            Platform.runLater(() -> statsDialog.setContentText(MainApplication.instance.habitat.getStatisticString(MainApplication.instance.elapsed)));
        }
    }

    public void onExitApplicationMenuItemAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void onStartSimMenuItemAction(ActionEvent actionEvent) {
        MainApplication.instance.habitat.startSimulation();
    }

    public void onEndSimMenuItemAction(ActionEvent actionEvent) {
        MainApplication.instance.habitat.stopSimulation();
    }

    public void onShowTimeMenuItemAction(ActionEvent actionEvent) {
        CheckMenuItem item = (CheckMenuItem) actionEvent.getSource();
        RadioButton showRadBtn = (RadioButton) MainApplication.instance.habitat.getRoot().getLeft()
                .lookup("#showTimeRadioBtn");
        RadioButton hideRadBtn = (RadioButton) MainApplication.instance.habitat.getRoot().getLeft()
                .lookup("#hideTimeRadioBtn");

        showRadBtn.setSelected(item.isSelected());
        hideRadBtn.setSelected(!item.isSelected());

        MainApplication.instance.habitat.setSimulationTimeVisibility(item.isSelected());
    }

    public void onShowStatsMenuItemAction(ActionEvent actionEvent) {
        CheckMenuItem item = (CheckMenuItem) actionEvent.getSource();
        CheckBox chkBox = (CheckBox) MainApplication.instance.habitat.getRoot().getLeft().lookup("#showStatsChkBox");
        chkBox.setSelected(item.isSelected());
    }

    public void onShowStatsChkBoxAction(ActionEvent actionEvent) {
        CheckBox chkBox = (CheckBox) actionEvent.getSource();
        MenuBar menuBar = (MenuBar) MainApplication.instance.habitat.getRoot().lookup("#menuBar");
        Menu menu = menuBar.getMenus().get(0);

        menu.getItems().stream().filter(x -> x instanceof CheckMenuItem && x.getId().equalsIgnoreCase("showStatsMenuItem")).findAny()
                .ifPresent(x -> ((CheckMenuItem) x).setSelected(chkBox.isSelected()));
    }

    @FXML
    private void onWorkerBeeLifetimeFieldAction(ActionEvent event) {
        try {
            WorkerBee.setLifetime(Integer.parseInt(workerBeeLifetimeField.getText()));
        } catch (NumberFormatException ex) {
            showInputError(((TextField) event.getSource()).getPromptText());
        }
    }

    @FXML
    private void onQueenBeeLifetimeFieldAction(ActionEvent event) {
        try {
            QueenBee.setLifetime(Integer.parseInt(queenBeeLifetimeField.getText()));
        } catch (NumberFormatException ex) {
            showInputError(((TextField) event.getSource()).getPromptText());
        }
    }

    @FXML
    private void onAliveBeesBtnClick(MouseEvent event) {
        if (MainApplication.instance.aliveBees == null) {
            MainApplication.instance.aliveBees = new AliveBeesDialog(MainApplication.instance.getStage().getOwner());
        }

        MainApplication.instance.aliveBees.show();
    }
}