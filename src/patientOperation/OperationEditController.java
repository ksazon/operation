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
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import patientOperation.*;
import patientOperation.model.Patient;
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
    //private ObservableList<String> diseasesListCombo;
    @FXML //
    private Stage dialogStage;
    @FXML //
    private Operation operation;
    @FXML //
    private DataAccessor dataAccessor;
    @FXML //
    private boolean okClicked = false;

    //private Main mainApp;
    @FXML
    private ObservableList<String> diseasesList = FXCollections.observableArrayList();
    //diseasesList = dataAccessor.getDisesesList();

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
        //diseaseNameField.setText(operation.getDiseaseName());
        //deathRateField.setText(operation.getDeathRate());
        diseasesListCombo.setItems(diseasesList);
        //birthDateField.setText(patient.getBirthDate());
        //birthDateField.setPromptText("dd.mm.yyyy");
    }

    //@FXML
    //public void setDiseasesList()
    @FXML  //
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws SQLException {
        if (isInputValid()) {
            operation.setDiseaseName(diseasesListCombo.getValue().toString());
            //operation.setDiseaseName(diseaseNameField.getText());
            //operation.setDeathRate(deathRateField.getText());

            //patient.setBirthDate(birthDateField.getText());

            //ainApp.addPatient(patient);

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    @FXML //
    private boolean isInputValid() {
        String errorMessage = "";
//
        if (diseasesListCombo.getValue().toString().length() == 0) {
            errorMessage += "Podaj nazwe choroby\n";
        }
//        if (diseaseNameField.getText() == null || diseaseNameField.getText().length() == 0) {
//            errorMessage += "Podaj nazwe choroby\n";
//        }
//        if (deathRateField.getText() == null || deathRateField.getText().length() == 0) {
//            errorMessage += "Podaj śmiertelność\n";
//        }

//        if (birthDateField.getText() == null || birthDateField.getText().length() == 0) {
//            errorMessage += "Podaj imię\n";
//        }

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