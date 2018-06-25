package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.service.api.PatientVisit;

public class PatientVisitWrapper implements TreeViewItemWrapper {

    private final PatientVisit patientVisit;

    public PatientVisitWrapper(PatientVisit patientVisit) {
        this.patientVisit = patientVisit;
    }

    @Override
    public String getFirstName() {
        return "";
    }

    @Override
    public String getLastName() {
        return "";
    }

    @Override
    public String getAge() {
        return "";
    }

    @Override
    public String getDob() {
        return "";
    }

    @Override
    public String getVisitDate() {
        return patientVisit.getPatientVisitDate().toString();
    }
}
