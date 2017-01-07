package patientOperation.model;

/**
 * Created by ks on 17.12.2016.
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Patient {
    private final StringProperty PESEL;
    private final StringProperty firstName;
    private final StringProperty lastName;

    //private final StringProperty address;
    private final StringProperty birthDate;
    //private final ObjectProperty<LocalDate> birthDate;

    public Patient() {
        this(null, null, null);
    }

    public Patient( String firstName, String lastName, String PESEL ) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.PESEL = new SimpleStringProperty(PESEL);

        //this.address = new SimpleStringProperty("Waniliowa 12");
        //this.birthDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(1989, 6, 17));
        this.birthDate = new SimpleStringProperty("");
    }

    //same gettery i settery do konca

    public String displayBirthDate() {
        String ret = PESEL.getValue().substring(0,2) + "-" + PESEL.toString().substring(2,4) + "-" + PESEL.toString().substring(4,6);

        return ret;
    }
    public String displayAge() {
        return String.valueOf( 2017 - Integer.parseInt( "19" + PESEL.getValue().substring(0,2) ));
    }
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

    public String getBirthDate() {
        return birthDate.get();
    }

    public StringProperty birthDateProperty() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }
//    public String getAdress() {
//        return adress.get();
//    }
//
//    public StringProperty adressProperty() {
//        return adress;
//    }
//
//    public void setAdress(String adress) {
//        this.adress.set(adress);
//    }

//    public LocalDate getBirthDate() {
//        return birthDate.get();
//    }
//
//    public ObjectProperty<LocalDate> birthDateProperty() {
//        return birthDate;
//    }
//
//    public void setBirthDate(LocalDate birthDate) {
//        this.birthDate.set(birthDate);
//    }



}
