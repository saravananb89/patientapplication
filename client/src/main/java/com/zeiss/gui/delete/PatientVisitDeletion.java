package com.zeiss.gui.delete;

import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.data.PatientVisit;

public class PatientVisitDeletion {
    public static boolean showPatientDeletionDialog(PatientService patientService, PatientVisit patientVisit, Runnable runnable){
        boolean b = patientService.deleteVisit(patientVisit);
        runnable.run();
        return b;
    }
}
