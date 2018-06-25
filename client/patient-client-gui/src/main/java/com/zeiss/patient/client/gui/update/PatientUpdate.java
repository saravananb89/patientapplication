package com.zeiss.patient.client.gui.update;

import com.zeiss.patient.client.gui.create.PatientCreation;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;

public class PatientUpdate extends PatientCreation {
    protected void save(PatientService patientService, Patient patient) {
        patientService.update(patient);
    }

}
