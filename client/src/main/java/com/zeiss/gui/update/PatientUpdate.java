package com.zeiss.gui.update;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.create.PatientCreation;

public class PatientUpdate extends PatientCreation {
    protected void save(PatientService patientService, Patient patient) {
        patientService.update(patient);
    }

}
