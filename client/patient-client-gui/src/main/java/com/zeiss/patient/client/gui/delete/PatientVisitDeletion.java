package com.zeiss.patient.client.gui.delete;

import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;

public class PatientVisitDeletion {
    public static boolean showPatientDeletionDialog(PatientService patientService, PatientVisit patientVisit, Runnable runnable){
        boolean b = patientService.deleteVisit(patientVisit);
        runnable.run();
        return b;
    }
}
