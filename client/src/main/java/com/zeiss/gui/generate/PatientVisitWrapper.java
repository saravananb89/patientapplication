package com.zeiss.gui.generate;

import com.zeiss.gui.data.PatientVisit;

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
