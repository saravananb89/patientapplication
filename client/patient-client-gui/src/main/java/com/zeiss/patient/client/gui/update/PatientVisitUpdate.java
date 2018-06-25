package com.zeiss.patient.client.gui.update;

import com.zeiss.patient.client.gui.create.PatientVisitCreation;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;

public class PatientVisitUpdate extends PatientVisitCreation {
    protected void save(PatientService patientService, PatientVisit patientVisit) {
        patientService.updateVisit(patientVisit);
    }
}
