package me.twintailedfoxxx.itlabs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.twintailedfoxxx.itlabs.objects.Bee;

import java.io.IOException;

@SuppressWarnings("unchecked")
public class AliveBeesDialog extends Dialog<Void> {
    @FXML
    private TableView<Bee> aliveBeesTable;

    public AliveBeesDialog() {
        super();
    }

    public AliveBeesDialog(Window owner) {
        super();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AliveBeesDialog.class.getResource("alive-bees.fxml"));
            initOwner(owner);
            setTitle("Alive Bees");

            DialogPane dialogPane = loader.load();
            setDialogPane(dialogPane);

            Stage stage = (Stage) getDialogPane().getScene().getWindow();
            stage.setOnCloseRequest(event -> close());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        TableColumn<Bee, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());

        TableColumn<Bee, Integer> spawnDelayCol = new TableColumn<>("Spawn Delay (s)");
        spawnDelayCol.setCellValueFactory(cellData -> cellData.getValue().spawnDelayProperty().asObject());

        TableColumn<Bee, Integer> lifeTimeCol = new TableColumn<>("Lifetime (s)");
        lifeTimeCol.setCellValueFactory(cellData -> cellData.getValue().lifeTimeProperty().asObject());

        TableColumn<Bee, Long> birthTimeCol = new TableColumn<>("Birth time (ms)");
        birthTimeCol.setCellValueFactory(cellData -> cellData.getValue().birthTimeProperty().asObject());

        aliveBeesTable.getColumns().addAll(idCol, spawnDelayCol, lifeTimeCol, birthTimeCol);
        ObservableList<Bee> bees = FXCollections.observableArrayList(MainApplication.instance.habitat
                .getBeeByBirthTimeMap().values());
        aliveBeesTable.setItems(bees);
    }

    public void updateTable() {
        ObservableList<Bee> bees = FXCollections.observableArrayList(MainApplication.instance.habitat
                .getBeeByBirthTimeMap().values());
        if (aliveBeesTable == null) {
            TableView<Bee> tblView = (TableView<Bee>) getDialogPane().lookup("#aliveBeesTable");
            tblView.setItems(bees);
        } else {
            aliveBeesTable.setItems(bees);
        }
    }
}
