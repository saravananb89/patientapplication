package com.zeiss.gui.data;

import java.util.List;

public interface PatientService {
    List<Patient> getPatients();
    List<PatientVisit> getPatientVisits();
    boolean delete(Patient patient);
    boolean deleteVisit(PatientVisit patientVisit);
    void create(Patient patient);
    void createVisit(PatientVisit patientVisit);
    void update(Patient patient);
    void updateVisit(PatientVisit patientVisit);
    List<Patient> getPatientsByLastName(String lastName);
    List<PatientVisit> getVisitPatientsByLastName(String lastName);
    List<Patient> getPatientsByFirstNameAndLastName(String firstName, String lastName);
    void clear();
    void clearVisit();
}
