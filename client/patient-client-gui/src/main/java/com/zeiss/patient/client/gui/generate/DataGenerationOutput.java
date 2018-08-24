package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientVisit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataGenerationOutput {

    private List<Patient> patients = new ArrayList<>();

    private List<PatientVisit> visits = new ArrayList<>();

    private Map<Patient,List<PatientVisit>> patientToVisitMap = new HashMap<>();

    public DataGenerationOutput() {
    }

    public DataGenerationOutput(List<Patient> patients, List<PatientVisit> visits, Map<Patient, List<PatientVisit>> patientToVisitMap) {
        this.patients = patients;
        this.visits = visits;
        this.patientToVisitMap = patientToVisitMap;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<PatientVisit> getVisits() {
        return visits;
    }

    public void setVisits(List<PatientVisit> visits) {
        this.visits = visits;
    }

    public Map<Patient, List<PatientVisit>> getPatientToVisitMap() {
        return patientToVisitMap;
    }

    public void setPatientToVisitMap(Map<Patient, List<PatientVisit>> patientToVisitMap) {
        this.patientToVisitMap = patientToVisitMap;
    }
}
