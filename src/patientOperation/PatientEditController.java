package patientOperation;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import patientOperation.*;
import patientOperation.model.Patient;

import java.sql.SQLException;

/**
 * Created by ks on 06.01.2017.
 */
public class PatientEditController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField PESELField;
    @FXML
    private TextField birthDateField;
    @FXML //
    private Stage dialogStage;
    @FXML //
    private Patient patient;
    @FXML //
    private DataAccessor dataAccessor;
    @FXML //
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage (Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    @FXML
    public void setPatient (Patient patient) {
        this.patient = patient;

        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        PESELField.setText(patient.getPESEL());
        //birthDateField.setText(patient.getBirthDate());
        //birthDateField.setPromptText("dd.mm.yyyy");
    }
    @FXML  //
    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOk() throws SQLException{
        if (isInputValid()) {
            patient.setFirstName(firstNameField.getText());
            patient.setLastName(lastNameField.getText());
            patient.setPESEL(PESELField.getText());
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

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0 ) {
            errorMessage += "Podaj imię\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0 ) {
            errorMessage += "Podaj nazwisko\n";
        }
        if (PESELField.getText() == null || PESELField.getText().length() != 11 ) {
            errorMessage += "Zły format PESEL (11 znaków)\n";
        }
//        if (birthDateField.getText() == null || birthDateField.getText().length() == 0 ) {
//            errorMessage += "Podaj imię\n";
//        }

        if (errorMessage.length() == 0) {
            return true;
        }
        else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Popraw pola");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
