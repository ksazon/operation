package patientOperation;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import patientOperation.model.Operation;
import patientOperation.model.Patient;
//import com.mysql.*;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private DataAccessor dataAccessor;

    public ObservableList<Patient> getPatientData() throws Exception {
        //return patientData;
        return dataAccessor.getPatientList();
    }

    public ObservableList<String> getDiseasesList() throws Exception {
        return dataAccessor.getDisesesList();
    }

    public ObservableList<Operation> getOperationData(Patient patient) throws Exception {
        return dataAccessor.getOperationList(patient);
    }

    public void removePatient(String deletedPESEL) throws SQLException {
        dataAccessor.removePatient(deletedPESEL);
    }

    public void removeOperation(Integer deletedId) throws SQLException {
        dataAccessor.removeOperation( deletedId );
    }

    public void addPatient(Patient newPatient) throws SQLException {
        dataAccessor.addPatient(newPatient);
    }

    public void addOperation(Operation newOperation, Patient patient) throws SQLException {
        dataAccessor.addOperation(newOperation, patient);
    }

    public void editOperation(Operation editedOperation) throws SQLException {
        dataAccessor.editOperation(editedOperation);
    }

    public void editPatient(Patient editedPatient, String oldPESEL) throws SQLException {
        dataAccessor.editPatient(editedPatient, oldPESEL);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String DRIVER = "com.mysql.jdbc.Driver";
        String HOST = "jdbc:mysql://aa1usznuujiim25.ckjkyoknuuox.us-west-2.rds.amazonaws.com:3306";
        String USER = "sazkr";
        String PASS = "Z8U8pxGW";
        dataAccessor = new DataAccessor(DRIVER, HOST, USER, PASS);

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dziennik operacji");

        initRootLayout();
        showPatientOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showPatientOverview() throws Exception{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("OperationOverview.fxml"));
            AnchorPane patientOverview = loader.load();

            rootLayout.setCenter(patientOverview);

            OperationOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public boolean showPatientEditDetail(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PatientEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja pacjenta");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PatientEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showOperationEditDetail(Operation operation) throws SQLException, Exception {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("OperationEditDialog.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edycja operacji");

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            OperationEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setOperation(operation, this);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}