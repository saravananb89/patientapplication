package com.zeiss.patient.client.gui.delete;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;

public class PatientDeletion {

    public static boolean showPatientDeletionDialog(PatientService patientService, Patient patient, Runnable runnable){
       boolean b = patientService.delete(patient);
        runnable.run();
        return b;
    }
}
