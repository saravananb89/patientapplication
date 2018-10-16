package com.zeiss.patient.service.api;

import java.util.List;

public interface PatientService {
    List<? extends Patient> getPatients();
    Patient getPatientsById(String id);
    PatientVisit getPatientVisitById(String id);
    List<? extends PatientVisit> getPatientVisits();
    boolean delete(Patient patient);
    boolean deleteVisit(PatientVisit patientVisit);
    void create(Patient patient);
    void createVisit(PatientVisit patientVisit);
    void update(Patient patient);
    void updateVisit(PatientVisit patientVisit);
    List<? extends Patient> getPatientsByLastName(String lastName);
    List<? extends Patient> getPatientsByFirstName(String firstName);
    List<? extends PatientVisit> getVisitPatientsByLastName(String lastName);
    //List<? extends PatientVisit> getVisitPatientsById(String id);
    List<? extends Patient> getPatientsByFirstNameAndLastName(String firstName, String lastName);
    List<? extends PatientVisit> getVisitPatientsByFirstNameAndLastName(String firstName, String lastName);
    void clear();
    void clearVisit();
}
