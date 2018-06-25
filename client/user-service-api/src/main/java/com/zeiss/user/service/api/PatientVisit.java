package com.zeiss.patient.service.api;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public interface PatientVisit {

    String getId();

    StringProperty idProperty();

    void setId(String id);

    String getVisitPatientFirstName();

    StringProperty visitPatientFirstNameProperty();


    void setVisitPatientFirstName(String visitPatientFirstName);

    String getVisitPatientLastName();

    StringProperty visitPatientLastNameProperty();

    void setVisitPatientLastName(String visitPatientLastName);

    LocalDate getPatientVisitDate();

    ObjectProperty<LocalDate> patientVisitDateProperty();

    void setPatientVisitDate(String patientVisitDate);


}