package com.zeiss.patient.client.gui.plan;

import com.zeiss.patient.service.api.Patient;
import javafx.scene.text.TextFlow;

public class PatientTextFlow extends TextFlow {

    private final Patient patient;

    public PatientTextFlow(Patient patient) {
        this.patient = patient;
    }

    public Patient getPatient() {
        return patient;
    }
}
