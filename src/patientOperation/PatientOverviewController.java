package patientOperation;

/**
 * Created by ks on 26.12.2016.
 */

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import patientOperation.Main;
import patientOperation.model.Operation;
import patientOperation.model.Patient;
import patientOperation.DataAccessor;

import java.sql.SQLException;

public class PatientOverviewController {
    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, String> firstNameColumn;
    @FXML
    private TableColumn<Patient, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label PESELLabel;
//    @FXML
//    private Label postalCodeLabel;
//    @FXML
//    private Label cityLabel;
//    @FXML
//    private Label birthdayLabel;

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


    // Reference to the main application.
    private Main mainApp;
    private DataAccessor dataAccessor;
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PatientOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()throws Exception {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        diseaseNameColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseNameProperty());
        deathRateColumn.setCellValueFactory(cellData -> cellData.getValue().deathRateProperty());

        showPatientDetails(null);
        //showDiseaseDetails(null);

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

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) throws Exception {
        this.mainApp = mainApp;

        // Add observable list data to the table
        patientTable.setItems(mainApp.getPatientData());
        operationTable.setItems(mainApp.getOperationData(null));
        //patientTable.setEditable(true);
    }

    private void showPatientDetails(Patient patient) {
        if ( patient != null ) {
            firstNameLabel.setText( patient.getFirstName() );
            lastNameLabel.setText( patient.getLastName() );
            PESELLabel.setText( patient.getPESEL() );
        }
        else {
            firstNameLabel.setText( "" );
            lastNameLabel.setText( "" );
            PESELLabel.setText( "" );
        }
    }

    private void showDiseaseDetails(Operation operation) {
        if ( operation != null ) {
            diseaseNameLabel.setText( operation.getDiseaseName() );
            deathRateLabel.setText( operation.getDeathRate() );
            //PESELLabel.setText( patient.getPESEL() );
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
        //dataAccessor = new DataAccessor()
        //int selectedIndex = patientTable.getSelectionModel().getSelectedIndex();
        String deletedPESEL = patientTable.getSelectionModel().getSelectedItem().getPESEL();
        //patientTable.getItems().remove(selectedIndex);
        mainApp.removePatient( deletedPESEL );
        patientTable.setItems(mainApp.getPatientData());
        operationTable.setItems(mainApp.getOperationData(null));
        //patientTable.refresh();
        //operationTable.refresh();
    }

    @FXML
    private void handleDeleteOperation() throws Exception {
        Integer deletedId = operationTable.getSelectionModel().getSelectedItem().getId();
        mainApp.removeOperation( deletedId );
        Alert alert = new Alert((Alert.AlertType.WARNING));
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("dupa");
        alert.setHeaderText("noga");
        alert.setContentText("nufa");
        alert.showAndWait();



    }
}
