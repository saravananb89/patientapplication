package com.zeiss.patient.client.gui;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.user.service.api.User;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PatientModel {

    //Patients

    private ObjectProperty<Patient> selectedPatient = new SimpleObjectProperty<>();

    private BooleanProperty deletionImPossible = new SimpleBooleanProperty();

    private BooleanProperty updateImPossible = new SimpleBooleanProperty();

    private ListProperty<Patient> patients = new SimpleListProperty<>();

    private BooleanProperty openPatientImPossible = new SimpleBooleanProperty();

    //Patient Visits
    private ListProperty<PatientVisit> visitPatients = new SimpleListProperty<>();

    private ObjectProperty<PatientVisit> selectedVisitPatient = new SimpleObjectProperty<>();

    private BooleanProperty deletionVisitImPossible = new SimpleBooleanProperty();

    private BooleanProperty updateVisitImPossible = new SimpleBooleanProperty();

    //User
    private ListProperty<User> users = new SimpleListProperty<>();

    private ObjectProperty<User> selectedUser = new SimpleObjectProperty<>();

    private BooleanProperty deletionUserImPossible = new SimpleBooleanProperty();

    private BooleanProperty updateUserImPossible = new SimpleBooleanProperty();


    public PatientModel() {
        deletionImPossible.bind(selectedPatient.isNull());
        updateImPossible.bind(selectedPatient.isNull());
        deletionVisitImPossible.bind(selectedVisitPatient.isNull());
        updateVisitImPossible.bind(selectedVisitPatient.isNull());
        openPatientImPossible.bind(selectedPatient.isNull());
        deletionUserImPossible.bind(selectedUser.isNull());
        updateUserImPossible.bind(selectedUser.isNull());
    }

    public boolean getOpenPatientImPossible() {
        return openPatientImPossible.get();
    }

    public BooleanProperty openPatientImPossibleProperty() {
        return openPatientImPossible;
    }

    public void setOpenPatientImPossible(boolean openPatientImPossible) {
        this.openPatientImPossible.set(openPatientImPossible);
    }

    public ObservableList<Patient> getPatients() {
        return patients.get();
    }

    public ListProperty<Patient> patientsProperty() {
        return patients;
    }

    public void setPatients(List<? extends Patient> patients) {
        this.patients.setValue(FXCollections.observableArrayList(patients));
    }

    public Patient getSelectedPatient() {
        return selectedPatient.get();
    }

    public ObjectProperty<Patient> selectedPatientProperty() {
        return selectedPatient;
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient.set(selectedPatient);
    }

    public boolean isDeletionImPossible() {
        return deletionImPossible.get();
    }

    public BooleanProperty deletionImPossibleProperty() {
        return deletionImPossible;
    }

    public boolean isUpdateImPossible() {
        return updateImPossible.get();
    }

    public BooleanProperty updateImPossibleProperty() {
        return updateImPossible;
    }

    public ObservableList<PatientVisit> getVisitPatients() {
        return visitPatients.get();
    }

    public ListProperty<PatientVisit> visitPatientsProperty() {
        return visitPatients;
    }

    public void setVisitPatients(List<? extends PatientVisit> visitPatients) {
        this.visitPatients.setValue(FXCollections.observableArrayList(visitPatients));
    }

    public PatientVisit getSelectedVisitPatient() {
        return selectedVisitPatient.get();
    }

    public ObjectProperty<PatientVisit> selectedVisitPatientProperty() {
        return selectedVisitPatient;
    }

    public void setSelectedVisitPatient(PatientVisit selectedVisitPatient) {
        this.selectedVisitPatient.set(selectedVisitPatient);
    }

    public boolean isDeletionVisitImPossible() {
        return deletionVisitImPossible.get();
    }

    public BooleanProperty deletionVisitImPossibleProperty() {
        return deletionVisitImPossible;
    }

    public boolean isUpdateVisitImPossible() {
        return updateVisitImPossible.get();
    }

    public BooleanProperty updateVisitImPossibleProperty() {
        return updateVisitImPossible;
    }

    public ObservableList<User> getUsers() {
        return users.get();
    }

    public ListProperty<User> usersProperty() {
        return users;
    }

    public void setUsers(List<? extends User> users) {
        this.users.setValue(FXCollections.observableArrayList(users));
    }

    public User getSelectedUser() {
        return selectedUser.get();
    }

    public ObjectProperty<User> selectedUserProperty() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser.set(selectedUser);
    }

    public boolean isDeletionUserImPossible() {
        return deletionUserImPossible.get();
    }

    public BooleanProperty deletionUserImPossibleProperty() {
        return deletionUserImPossible;
    }

    public boolean isUpdateUserImPossible() {
        return updateUserImPossible.get();
    }

    public BooleanProperty updateUserImPossibleProperty() {
        return updateUserImPossible;
    }

    @Override
    public String toString() {
        return "PatientModel{" +
                "selectedPatient=" + selectedPatient +
                ", deletionImPossible=" + deletionImPossible +
                ", updateImPossible=" + updateImPossible +
                ", patients=" + patients +
                ", openPatientImPossible=" + openPatientImPossible +
                ", visitPatients=" + visitPatients +
                ", selectedVisitPatient=" + selectedVisitPatient +
                ", deletionVisitImPossible=" + deletionVisitImPossible +
                ", updateVisitImPossible=" + updateVisitImPossible +
                ", users=" + users +
                ", selectedUser=" + selectedUser +
                ", deletionUserImPossible=" + deletionUserImPossible +
                ", updateUserImPossible=" + updateUserImPossible +
                '}';
    }
}
