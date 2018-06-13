package com.zeiss.gui.update;

import com.zeiss.gui.create.PatientVisitCreation;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.data.PatientVisit;

public class PatientVisitUpdate extends PatientVisitCreation{
    protected void save(PatientService patientService, PatientVisit patientVisit) {
        patientService.updateVisit(patientVisit);
    }
}
