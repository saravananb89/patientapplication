package com.zeiss.gui.generate;

import com.zeiss.gui.data.Patient;

public class PatientWrapper implements TreeViewItemWrapper {

    private final Patient patient;

    public PatientWrapper(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String getFirstName() {
        return patient.getFirstName();
    }

    @Override
    public String getLastName() {
        return patient.getLastName();
    }

    @Override
    public String getAge() {
        return patient.getAge();
    }

    @Override
    public String getDob() {
        return patient.getDob().toString();
    }

    @Override
    public String getVisitDate() {
        return "";
    }
}
