package patientOperation;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import patientOperation.model.Operation;
import patientOperation.model.Patient;
//import com.mysql.*;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

//    private ObservableList<Patient> patientData = FXCollections.observableArrayList();
    private DataAccessor dataAccessor;

//    public Main() {
//        patientData.add(new Patient("Jarek", "Jaro", "90"));
//        patientData.add(new Patient("Jarek2", "Jaro", "90"));
//        patientData.add(new Patient("Jarek3", "Jaro", "90"));
//    }

    public ObservableList<Patient> getPatientData() throws Exception {
        //return patientData;
        return dataAccessor.getPatientList();
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

    @Override
    public void start(Stage primaryStage) throws Exception {

        dataAccessor = new DataAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://aa1usznuujiim25.ckjkyoknuuox.us-west-2.rds.amazonaws.com:3306", "sazkr", "Z8U8pxGW");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Patient");

        initRootLayout();

        showPatientOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPatientOverview() throws Exception{
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PatientOverview.fxml"));
            AnchorPane patientOverview = loader.load(); //(AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(patientOverview);

            PatientOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}