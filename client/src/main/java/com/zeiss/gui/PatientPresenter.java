package com.zeiss.gui;

import com.zeiss.gui.create.PatientVisitCreation;
import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.create.PatientCreation;
import com.zeiss.gui.data.PatientVisit;
import com.zeiss.gui.delete.PatientDeletion;
import com.zeiss.gui.delete.PatientVisitDeletion;
import com.zeiss.gui.generate.GeneratePatient;
import com.zeiss.gui.search.PatientSearch;
import com.zeiss.gui.search.PatientVisitSearch;
import com.zeiss.gui.update.PatientUpdate;
import com.zeiss.gui.update.PatientVisitUpdate;

import javax.inject.Inject;
import javax.inject.Provider;

public class PatientPresenter {

    private final PatientView patientView;
    private final PatientModel patientModel;

    private final PatientService patientService;
    @Inject
    private Provider<GeneratePatient> generatePatient;

    @Inject
    public PatientPresenter(PatientView patientView, PatientService patientService) {
        this.patientView = patientView;
        this.patientModel = new PatientModel();
        this.patientService = patientService;
    }

    public void loadPatientData() {
        patientModel.setPatients(patientService.getPatients());
    }

    public void loadPatientVisitData()
    {
        patientModel.setVisitPatients(patientService.getPatientVisits());
       System.out.println( patientModel.getVisitPatients());
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
        new PatientSearch().showPatientSearchDialog(patientService, patientView.getStage(), patientView.getLocaleService().getLocale());
    }

    public void deleteAction() {
        PatientDeletion.showPatientDeletionDialog(patientService,
                patientModel.getSelectedPatient(), this::loadPatientData);
    }

    public void createAction() {
        new PatientCreation().showPatientDialog(patientService, new Patient(), this::loadPatientData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void updateAction() {
        new PatientUpdate().showPatientDialog(patientService, patientModel.getSelectedPatient(), this::loadPatientData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void deleteVisitAction() {
        PatientVisitDeletion.showPatientDeletionDialog(patientService,
                patientModel.getSelectedVisitPatient(), this::loadPatientVisitData);
    }

    public void createVisitAction() {
        new PatientVisitCreation().showPatientDialog(patientService, new PatientVisit(), this::loadPatientVisitData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void updateVistAction() {
        new PatientVisitUpdate().showPatientDialog(patientService, patientModel.getSelectedVisitPatient(), this::loadPatientVisitData, patientView.getStage(),
                patientView.getLocaleService().getLocale());

    }

    public void searchVisitAction() {
        new PatientVisitSearch().showPatientSearchDialog(patientService, patientView.getStage(), patientView.getLocaleService().getLocale());

    }

    public void generateAction() {
        generatePatient.get().showPatientDialog(new Patient(), patientView.getStage());

    }

    public void initialize() {
        patientView.bindToModel(patientModel);
        loadPatientData();
        loadPatientVisitData();
    }
}
