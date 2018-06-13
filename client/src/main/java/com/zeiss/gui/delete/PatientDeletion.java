package com.zeiss.gui.delete;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;

public class PatientDeletion {

    public static boolean showPatientDeletionDialog(PatientService patientService, Patient patient, Runnable runnable){
       boolean b = patientService.delete(patient);
        runnable.run();
        return b;
    }
}
