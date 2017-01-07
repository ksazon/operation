package patientOperation;

/**
 * Created by ks on 26.12.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import patientOperation.model.Operation;
import patientOperation.model.Patient;

import java.sql.SQLException;

public class OperationOverviewController {
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;
    @FXML
    private TableColumn<Patient, String> PESELColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label PESELLabel;
    @FXML
    private Label ageLabel;

    @FXML
    private TableView<Operation> operationTable;
    @FXML
    private TableColumn<Operation, String> diseaseNameColumn;
    @FXML
    private TableColumn<Operation, String> deathRateColumn;

    @FXML
    private Label diseaseNameLabel;
    @FXML
    private  Label deathRateLabel;

    private Main mainApp;

    public OperationOverviewController() {
    }

    @FXML
    private void initialize()throws Exception {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        PESELColumn.setCellValueFactory(cellData -> cellData.getValue().PESELProperty());

        diseaseNameColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseNameProperty());
        deathRateColumn.setCellValueFactory(cellData -> cellData.getValue().deathRateProperty());

        showPatientDetails(null);
        showDiseaseDetails(null);

        patientTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showPatientDetails(newValue)));
        patientTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            try {
                showPatientDiseases(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        operationTable.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> showDiseaseDetails(newValue)));
    }

    public void setMainApp(Main mainApp) throws Exception {
        this.mainApp = mainApp;

        patientTable.setItems(mainApp.getPatientData());
        operationTable.setItems(mainApp.getOperationData(null));
    }

    private void showPatientDetails(Patient patient) {
        if ( patient != null ) {
            firstNameLabel.setText( patient.getFirstName() );
            lastNameLabel.setText( patient.getLastName() );
            PESELLabel.setText( patient.getPESEL() );
            ageLabel.setText( patient.displayAge() );
        }
        else {
            firstNameLabel.setText( "" );
            lastNameLabel.setText( "" );
            PESELLabel.setText( "" );
            ageLabel.setText( "" );
        }
    }

    private void showDiseaseDetails(Operation operation) {
        if ( operation != null ) {
            diseaseNameLabel.setText( operation.getDiseaseName() );
            deathRateLabel.setText( operation.getDeathRate() );
        }
        else {
            diseaseNameLabel.setText( "" );
            deathRateLabel.setText( "" );
        }
    }

    private void showPatientDiseases(Patient patient) throws Exception{
        if ( patient != null ) {
            operationTable.setItems( mainApp.getOperationData(patient) );
        }
    }

    @FXML
    private void handleDeletePatient() throws Exception, SQLException {
        String deletedPESEL = patientTable.getSelectionModel().getSelectedItem().getPESEL();
        mainApp.removePatient( deletedPESEL );
        patientTable.setItems(mainApp.getPatientData());
        operationTable.setItems(mainApp.getOperationData(null));
    }

    @FXML
    private void handleDeleteOperation() throws Exception {
        Integer deletedId = operationTable.getSelectionModel().getSelectedItem().getId();
        mainApp.removeOperation( deletedId );
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("aj waj");
        alert.setHeaderText("noga");
        alert.setContentText("nufa");
        alert.showAndWait();
        operationTable.setItems((mainApp.getOperationData(patientTable.getSelectionModel().getSelectedItem())));
    }

    @FXML
    private void handleAddPatient() throws Exception {
        Patient newPatient = new Patient();
        boolean okClicked = mainApp.showPatientEditDetail(newPatient);
        if (okClicked) {
            mainApp.addPatient(newPatient);
            patientTable.setItems(mainApp.getPatientData());
        }
    }

    @FXML
    private void handleEditPatient() throws SQLException, Exception {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        //String oldPesel = selectedPatient.getPESEL();
        String oldPESEL = selectedPatient.getPESEL();
        if (selectedPatient != null) {
            boolean okClicked = mainApp.showPatientEditDetail(selectedPatient);
            if (okClicked) {
                mainApp.editPatient(selectedPatient, oldPESEL);
                showPatientDetails(selectedPatient);
                patientTable.setItems(mainApp.getPatientData());
             }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak pacjenta");
            alert.setHeaderText("Nie został zaznaczony żaden pacjent");
            alert.setContentText("Zaznacz pacjeta w tabeli");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddOperation() throws Exception {
        Operation newOperation = new Operation();
        boolean okClicked = mainApp.showOperationEditDetail(newOperation);
        if (okClicked) {
            mainApp.addOperation(newOperation, patientTable.getSelectionModel().getSelectedItem());
            operationTable.setItems(mainApp.getOperationData(patientTable.getSelectionModel().getSelectedItem()));
        }
    }

    @FXML
    private void handleEditOperation() throws SQLException, Exception {
        Operation selectedOperation = operationTable.getSelectionModel().getSelectedItem();

        Integer oldId = selectedOperation.getId();
        if (selectedOperation != null) {
            boolean okClicked = mainApp.showOperationEditDetail(selectedOperation);
            if (okClicked) {
                mainApp.editOperation(selectedOperation);
                showDiseaseDetails(selectedOperation);
                patientTable.setItems(mainApp.getPatientData());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("Brak operacji");
            alert.setHeaderText("Nie została zaznaczona żadna operacja");
            alert.setContentText("Zaznacz operacje w tabeli");

            alert.showAndWait();
        }
    }
}
