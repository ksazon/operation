package patientOperation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import patientOperation.model.Operation;
import patientOperation.model.Patient;
import sun.security.tools.keytool.Resources_sv;

import java.sql.Connection ;
import java.sql.DriverManager ;
import java.sql.SQLException ;
import java.sql.Statement ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;

/**
 * Created by ks on 01.01.2017.
 */


public class DataAccessor {

    // in real life, use a connection pool....
    private Connection connection ;

    public DataAccessor(String driverClassName, String dbURL, String user, String password) throws SQLException, ClassNotFoundException {
        try {
            Class.forName(driverClassName);
        }
        catch ( ClassNotFoundException ex ) {
            ex.printStackTrace();
        }
        connection = DriverManager.getConnection(dbURL, user, password);
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public ObservableList<Patient> getPatientList() throws SQLException {
        try (
                Statement stmnt = connection.createStatement();
                Statement stmnt2 = connection.createStatement();
                ResultSet rs2 = stmnt2.executeQuery("USE ebdb");
                ResultSet rs = stmnt.executeQuery("select * from patient");
        ){
            ObservableList<Patient> patientList = FXCollections.observableArrayList(); //new ArrayList<>();
            while (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String PESEL = rs.getString("PESEL");
                Patient patient = new Patient(firstName, lastName, PESEL);
                patientList.add(patient);
            }
            return patientList ;
        }
    }

    public ObservableList<Operation> getOperationList(Patient patient) throws SQLException {
        String p = "SELECT * FROM ebdb.patient_operation LEFT OUTER JOIN" +
                " ebdb.operation ON operation.diseaseName = patient_operation.diseaseName WHERE patient_operation.PESEL = ?";
        PreparedStatement prestmnt = null;
        ResultSet rs;
        ObservableList<Operation> operationList = FXCollections.observableArrayList();
        if ( patient == null ) {
            return null;
        }
        try {
            //Statement stmnt = connection.createStatement();
            //ResultSet rs = stmnt.executeQuery("select * from ebdb.patient_operation WHERE patient_operation.diseaseName = ino");
            prestmnt = connection.prepareStatement(p);
            prestmnt.setString(1, patient.getPESEL());
            rs = prestmnt.executeQuery();
            //ResultSet rs = connection.prepareStatement(p);

            //ObservableList<Operation> operationList = FXCollections.observableArrayList(); //new ArrayList<>();
            while (rs.next()) {
                String diseaseName = rs.getString("diseaseName");
                String deathRate = rs.getString("deathRate");
                Integer id = rs.getInt("id");
//                String date = rs.getString("lastName");
//                String PESEL = rs.getString("PESEL");
                Operation operation = new Operation(diseaseName, deathRate, id);
                operationList.add(operation);
            }
            return operationList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return operationList;
    }

    public void addPatient( Patient newPatient ) throws SQLException {
        String ap = "INSERT INTO ebdb.patient (PESEL, firstName, lastName) VALUES (?,?,?)";
        PreparedStatement prestmnt = null;
        prestmnt = connection.prepareStatement(ap);
        prestmnt.setString(1, newPatient.getPESEL());
        prestmnt.setString(2, newPatient.getFirstName());
        prestmnt.setString(3, newPatient.getLastName());
        prestmnt.executeUpdate();
    }

    public void editPatient( Patient afterEditPatient, String oldPESEL ) throws SQLException {
        String ep = "UPDATE ebdb.patient SET PESEL=?, firstName=?, lastName=? WHERE patient.PESEL = ?";
        PreparedStatement prestmnt = null;
        prestmnt = connection.prepareStatement(ep);
        prestmnt.setString(1, afterEditPatient.getPESEL());
        prestmnt.setString(2, afterEditPatient.getFirstName());
        prestmnt.setString(3, afterEditPatient.getLastName());
        prestmnt.setString(4, oldPESEL);
        prestmnt.executeUpdate();
    }

    public void removePatient( String deletedPESEL ) throws SQLException {
        String dp = "DELETE FROM ebdb.patient WHERE patient.PESEL = ?";
        PreparedStatement prestmnt = null;
        prestmnt = connection.prepareStatement(dp);
        prestmnt.setString(1, deletedPESEL);
        prestmnt.executeUpdate();
    }

    public void removeOperation( Integer deletedId ) throws SQLException {
        String dop = "DELETE FROM ebdb.patient_operation WHERE patient_operation.id = ?";
        PreparedStatement prestmnt = null;
        prestmnt = connection.prepareStatement(dop);
        prestmnt.setInt(1, deletedId);
        prestmnt.executeUpdate();
    }

    public void addOperation( Operation newOperation, Patient patient ) throws SQLException {
        String ap = "INSERT INTO ebdb.patient_operation (PESEL, diseaseName) VALUES (?,?)";
        PreparedStatement prestmnt = null;
        prestmnt = connection.prepareStatement(ap);
        prestmnt.setString(1, patient.getPESEL());
        prestmnt.setString(2, newOperation.getDiseaseName());
        //prestmnt.setString(3, newPatient.getLastName());
        prestmnt.executeUpdate();
    }

    public ObservableList<String> getDisesesList () throws SQLException {
        String dl = "SELECT diseaseName FROM ebdb.operation";
        Statement stmnt = connection.createStatement();
        ResultSet rs;
        ObservableList<String> diseasesList = FXCollections.observableArrayList();
        rs = stmnt.executeQuery(dl);
        while ( rs.next() ) {
            String diseaseName = rs.getString("diseaseName");
            diseasesList.add(diseaseName);
        }
        return diseasesList;
    }
    }

    // other methods, eg. addPerson(...) etc
