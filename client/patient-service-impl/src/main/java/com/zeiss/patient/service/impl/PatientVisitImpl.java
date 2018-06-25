package com.zeiss.patient.service.impl;

import com.zeiss.patient.service.api.PatientVisit;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Objects;

public class PatientVisitImpl implements PatientVisit {

    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty visitPatientFirstName = new SimpleStringProperty();
    private final StringProperty visitPatientLastName = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> patientVisitDate = new SimpleObjectProperty<>();

    public PatientVisitImpl() {
    }

    public PatientVisitImpl(String id, String visitPatientFirstName, String visitPatientLastName, LocalDate patientVisitDate ) {
        this.id.setValue(id);
        this.visitPatientFirstName.setValue(visitPatientFirstName);
        this.visitPatientLastName.setValue(visitPatientLastName);
        this.patientVisitDate.setValue(patientVisitDate);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getVisitPatientFirstName() {
        return visitPatientFirstName.get();
    }

    public StringProperty visitPatientFirstNameProperty() {
        return visitPatientFirstName;
    }

    public void setVisitPatientFirstName(String visitPatientFirstName) {
        this.visitPatientFirstName.set(visitPatientFirstName);
    }

    public String getVisitPatientLastName() {
        return visitPatientLastName.get();
    }

    public StringProperty visitPatientLastNameProperty() {
        return visitPatientLastName;
    }

    public void setVisitPatientLastName(String visitPatientLastName) {
        this.visitPatientLastName.set(visitPatientLastName);
    }

    public LocalDate getPatientVisitDate() {
        return patientVisitDate.get();
    }

    public ObjectProperty<LocalDate> patientVisitDateProperty() {
        return patientVisitDate;
    }

    public void setPatientVisitDate(String patientVisitDate) {
        this.patientVisitDate.set(LocalDate.parse(patientVisitDate));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientVisitImpl that = (PatientVisitImpl) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PatientVisit{" +
                "id=" + id +
                ", visitPatientFirstName=" + visitPatientFirstName +
                ", visitPatientLastName=" + visitPatientLastName +
                ", patientVisitDate=" + patientVisitDate +
                '}';
    }
}