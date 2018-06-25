package com.zeiss.patient.client.gui;

import com.zeiss.patient.client.gui.clear.PatientClear;
import com.zeiss.patient.client.gui.clear.PatientVisitClear;
import com.zeiss.patient.client.gui.create.PatientCreation;
import com.zeiss.patient.client.gui.create.PatientVisitCreation;
import com.zeiss.patient.client.gui.delete.PatientDeletion;
import com.zeiss.patient.client.gui.delete.PatientVisitDeletion;
import com.zeiss.patient.client.gui.generate.GeneratePatient;
import com.zeiss.patient.client.gui.openpatient.OpenPatient;
import com.zeiss.patient.client.gui.search.PatientSearch;
import com.zeiss.patient.client.gui.search.PatientVisitSearch;
import com.zeiss.patient.client.gui.update.PatientUpdate;
import com.zeiss.patient.client.gui.update.PatientVisitUpdate;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;

import javax.inject.Inject;
import javax.inject.Provider;

public class PatientPresenter {

    private final PatientView patientView;
    private final PatientModel patientModel;

    private final PatientService patientService;
    @Inject
    private Provider<GeneratePatient> generatePatient;
    @Inject
    private Provider<PatientClear> patientClearProvider;
    @Inject
    private Provider<PatientVisitClear> patientVisitClearProvider;
    @Inject
    private Provider<OpenPatient> openPatient;
    @Inject
    private Provider<PatientUpdate> patientUpdateProvider;
    @Inject
    private Provider<PatientCreation> patientCreationProvider;
    @Inject
    private Provider<PatientSearch> patientSearchProvider;
    @Inject
    private Provider<PatientVisitSearch> patientVisitSearchProvider;
    @Inject
    private Provider<Patient> patientProvider;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;

    @Inject
    public PatientPresenter(PatientView patientView, PatientService patientService) {
        this.patientView = patientView;
        this.patientModel = new PatientModel();
        this.patientService = patientService;
    }

    public void loadPatientData() {
        patientModel.setPatients(patientService.getPatients());
    }

    public void loadPatientVisitData() {
        patientModel.setVisitPatients(patientService.getPatientVisits());
        System.out.println(patientModel.getVisitPatients());
    }

    public void selectPatient(Patient patient) {
        patientModel.setSelectedPatient(patient);
    }

    public void clearSelection() {
        patientModel.setSelectedPatient(null);
    }

    public void selectPatientVisit(PatientVisit patientVisit) {
        patientModel.setSelectedVisitPatient(patientVisit);
    }

    public void clearVisitSelection() {
        patientModel.setSelectedVisitPatient(null);
    }

    public void searchPatient() {
        patientSearchProvider.get().showPatientSearchDialog(patientView.getStage());
    }

    public void deleteAction() {
        PatientDeletion.showPatientDeletionDialog(patientService,
                patientModel.getSelectedPatient(), this::loadPatientData);
    }

    public void createAction() {
        patientCreationProvider.get().showPatientDialog(patientProvider.get(), this::loadPatientData, patientView.getStage());

    }

    public void updateAction() {
        patientUpdateProvider.get().showPatientDialog(patientModel.getSelectedPatient(), this::loadPatientData, patientView.getStage());

    }

    public void deleteVisitAction() {
        PatientVisitDeletion.showPatientDeletionDialog(patientService,
                patientModel.getSelectedVisitPatient(), this::loadPatientVisitData);
    }

    public void createVisitAction() {
        new PatientVisitCreation().showPatientDialog(patientService, patientVisitProvider.get(), this::loadPatientVisitData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void updateVistAction() {
        new PatientVisitUpdate().showPatientDialog(patientService, patientModel.getSelectedVisitPatient(), this::loadPatientVisitData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void searchVisitAction() {
        patientVisitSearchProvider.get().showPatientSearchDialog(patientView.getStage());

    }

    public void generateAction() {
        generatePatient.get().showPatientDialog(patientView.getStage());

    }

    public void clear() {
        patientClearProvider.get().showPatientDialog(patientService, patientView.getStage(), patientView.getLocaleService().getLocale());

    }

    public void clearVisit() {
        patientVisitClearProvider.get().showPatientDialog(patientService, patientView.getStage(), patientView.getLocaleService().getLocale());

    }

    public void initialize() {
        patientView.bindToModel(patientModel);
        loadPatientData();
        loadPatientVisitData();
    }

    public void openPatient() {
        openPatient.get().showPatientDialog(patientModel.getSelectedPatient(), patientView.getStage(), this::loadPatientData);
    }
}
