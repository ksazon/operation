package patientOperation;

/**
 * Created by ks on 06.01.2017.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import patientOperation.model.Operation;

import java.sql.SQLException;

/**
 * Created by ks on 06.01.2017.
 */
public class OperationEditController {
    @FXML
    private TextField diseaseNameField;
    @FXML
    private TextField deathRateField;
    @FXML
    private Label patientLabel;
    @FXML
    private ComboBox<String> diseasesListCombo;
    @FXML
    private ObservableList<String> diseasesList = FXCollections.observableArrayList();

    private Stage dialogStage;
    private Operation operation;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }
    @FXML
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    public void setOperation(Operation operation, Main mainApp) throws SQLException, Exception {
        this.operation = operation;
        diseasesList = mainApp.getDiseasesList();
        diseasesListCombo.setItems(diseasesList);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {
            operation.setDiseaseName(diseasesListCombo.getValue().toString());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (diseasesListCombo.getValue().toString().length() == 0) {
            errorMessage += "Podaj nazwe choroby\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Popraw pola");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}