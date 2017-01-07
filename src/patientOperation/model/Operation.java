package patientOperation.model;


import java.time.LocalDate;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

/**
 * Created by ks on 03.01.2017.
 */
public class Operation {
    //private final StringProperty id;
    private final StringProperty diseaseName;
    private final StringProperty deathRate;
    //private final ObjectProperty<LocalDate> operationDate;
    private final IntegerProperty id;

    public Operation() {
        this(null, null, 0);
    }

    public Operation(String diseaseName, String deathRate, Integer id) {
        this.diseaseName = new SimpleStringProperty(diseaseName);
        this.deathRate = new SimpleStringProperty(deathRate);
        //this.operationDate = new SimpleObjectProperty<LocalDate>(LocalDate.of(2017, 1, 1));
        this.id = new SimpleIntegerProperty(id);
    }

//    public String getId() {
//        return id.get();
//    }
//
//    public StringProperty idProperty() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id.set(id);
//    }
    //public ObservableList<String> getDisesesList()
    public Integer getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getDiseaseName() {
        return diseaseName.get();
    }

    public StringProperty diseaseNameProperty() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName.set(diseaseName);
    }

    public String getDeathRate() {
        return deathRate.get();
    }

    public StringProperty deathRateProperty() {
        return deathRate;
    }

    public void setDeathRate(String deathRate) {
        this.deathRate.set(deathRate);
    }

//    public LocalDate getOperationDate() {
//        return operationDate.get();
//    }
//
//    public ObjectProperty<LocalDate> operationDateProperty() {
//        return operationDate;
//    }
//
//    public void setOperationDate(LocalDate operationDate) {
//        this.operationDate.set(operationDate);
//    }
}
