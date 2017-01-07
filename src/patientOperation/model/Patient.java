package patientOperation.model;

/**
 * Created by ks on 17.12.2016.
 */

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Patient {
    private final StringProperty PESEL;
    private final StringProperty firstName;
    private final StringProperty lastName;

    public Patient() {
        this(null, null, null);
    }

    public Patient( String firstName, String lastName, String PESEL ) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.PESEL = new SimpleStringProperty(PESEL);
    }

    public String displayAge() {
        return String.valueOf( 2017 - Integer.parseInt( "19" + PESEL.getValue().substring(0,2) ));
    }

    //same gettery i settery do konca

    public String getPESEL() {
        return PESEL.get();
    }

    public StringProperty PESELProperty() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL.set(PESEL);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
