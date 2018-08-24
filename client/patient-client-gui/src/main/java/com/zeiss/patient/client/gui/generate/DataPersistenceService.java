package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import javax.inject.Inject;

public class DataPersistenceService extends Service<Void> {


    @Inject
    private PatientService patientService;

    private DataGenerationOutput output;

    public void setOutput(DataGenerationOutput output) {
        this.output = output;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int count = 0;
                for (Patient patient : output.getPatients()) {
                    count++;
                    updateProgress(count, output.getPatients().size());
                    updateMessage(count + " von " + output.getPatients().size() + " Patient persisted");
                    patientService.create(patient);
                    if (isCancelled()) {
                        return null;
                    }
                }
                count = 0;
                for (PatientVisit patientVisit : output.getVisits()) {
                    count++;
                    patientService.createVisit(patientVisit);
                    updateProgress(count, output.getVisits().size());
                    updateMessage(count + " von " + output.getVisits().size() + " Patient Visit persisted");
                    if (isCancelled()) {
                        return null;
                    }
                }
                return null;
            }


            @Override
            protected void succeeded() {
                super.succeeded();
                updateMessage("Done!");
            }

            @Override
            protected void cancelled() {
                super.cancelled();
                updateMessage("Cancelled!");
            }

            @Override
            protected void failed() {
                super.failed();
                updateMessage("Failed!");
            }
        };
    }
}
