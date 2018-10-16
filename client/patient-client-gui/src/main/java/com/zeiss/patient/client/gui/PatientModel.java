package com.zeiss.patient.client.gui;

import com.zeiss.device.service.api.Device;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.role.service.api.Role;
import com.zeiss.user.service.api.User;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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

    //Role
    private ListProperty<Role> roles = new SimpleListProperty<>();

    private ObjectProperty<Role> selectedRole = new SimpleObjectProperty<>();

    private BooleanProperty deletionRoleImPossible = new SimpleBooleanProperty();

    private BooleanProperty updateRoleImPossible = new SimpleBooleanProperty();

    //Device
    private ListProperty<Device> devices = new SimpleListProperty<>();

    private ObjectProperty<Device> selectedDevice = new SimpleObjectProperty<>();

    private BooleanProperty deletionDeviceImPossible = new SimpleBooleanProperty();

    private BooleanProperty updateDeviceImPossible = new SimpleBooleanProperty();

    private BooleanProperty hasPatientReadAccess = new SimpleBooleanProperty();
    private BooleanProperty hasVisitReadAccess = new SimpleBooleanProperty();
    private BooleanProperty hasUserReadAccess = new SimpleBooleanProperty();
    private BooleanProperty hasRoleReadAccess = new SimpleBooleanProperty();
    private BooleanProperty hasDeviceReadAccess = new SimpleBooleanProperty();

    private BooleanProperty hasPatientWriteAccess = new SimpleBooleanProperty();
    private BooleanProperty hasVisitWriteAccess = new SimpleBooleanProperty();
    private BooleanProperty hasUserWriteAccess = new SimpleBooleanProperty();
    private BooleanProperty hasRoleWriteAccess = new SimpleBooleanProperty();
    private BooleanProperty hasDeviceWriteAccess = new SimpleBooleanProperty();
    private BooleanProperty planImPossible = new SimpleBooleanProperty();

    private ObjectProperty<User> user = new SimpleObjectProperty<>();


    public PatientModel() {
        deletionImPossible.bind(selectedPatient.isNull());
        updateImPossible.bind(selectedPatient.isNull());
        deletionVisitImPossible.bind(selectedVisitPatient.isNull());
        updateVisitImPossible.bind(selectedVisitPatient.isNull());
        openPatientImPossible.bind(selectedPatient.isNull());
        deletionUserImPossible.bind(selectedUser.isNull());
        updateUserImPossible.bind(selectedUser.isNull());
        deletionRoleImPossible.bind(selectedRole.isNull());
        updateRoleImPossible.bind(selectedRole.isNull());
        deletionDeviceImPossible.bind(selectedDevice.isNull());
        updateDeviceImPossible.bind(selectedDevice.isNull());
        planImPossible.bind(selectedDevice.isNull());

        user.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                hasPatientReadAccess.unbind();
                hasVisitReadAccess.unbind();
                hasUserReadAccess.unbind();
                hasRoleReadAccess.unbind();
                hasDeviceReadAccess.unbind();
                hasPatientWriteAccess.unbind();
                hasVisitWriteAccess.unbind();
                hasUserWriteAccess.unbind();
                hasRoleWriteAccess.unbind();
                hasDeviceWriteAccess.unbind();
            }
            if (newValue != null) {
                BooleanBinding patientReadAccessBooleanBinding = Bindings.createBooleanBinding(() -> newValue.getRoleType().getPatientAccess().hasReadAccess(),
                        newValue.getRoleType().patientAccessProperty());
                hasPatientReadAccess.bind(patientReadAccessBooleanBinding);

                hasVisitReadAccess.bind(Bindings.createBooleanBinding(() -> {
                            System.out.println(newValue.getRoleType().getVisitAccess().hasReadAccess());
                            return newValue.getRoleType().getVisitAccess().hasReadAccess();
                        },
                        newValue.getRoleType().visitAccessProperty()));
                hasUserReadAccess.bind(Bindings.createBooleanBinding(() -> {
                            System.out.println(newValue.getRoleType().getVisitAccess().hasReadAccess());
                            return
                                    newValue.getRoleType().getUserAccess().hasReadAccess();
                        },
                        newValue.getRoleType().userAccessProperty()));
                hasRoleReadAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getRoleAccess().hasReadAccess(),
                        newValue.getRoleType().roleAccessProperty()));
                hasDeviceReadAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getDeviceAccess().hasReadAccess(),
                        newValue.getRoleType().deviceAccessProperty()));

                hasPatientWriteAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getPatientAccess().hasWriteAccess(),
                        newValue.getRoleType().patientAccessProperty()));
                hasVisitWriteAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getVisitAccess().hasWriteAccess(),
                        newValue.getRoleType().visitAccessProperty()));
                hasUserWriteAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getUserAccess().hasWriteAccess(),
                        newValue.getRoleType().userAccessProperty()));
                hasRoleWriteAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getRoleAccess().hasWriteAccess(),
                        newValue.getRoleType().roleAccessProperty()));
                hasDeviceWriteAccess.bind(Bindings.createBooleanBinding(() ->
                                newValue.getRoleType().getDeviceAccess().hasWriteAccess(),
                        newValue.getRoleType().deviceAccessProperty()));
            }
        });
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

    public boolean isPlanImPossible() {
        return planImPossible.get();
    }

    public BooleanProperty planImPossibleProperty() {
        return planImPossible;
    }

    public void setPlanImPossible(boolean planImPossible) {
        this.planImPossible.set(planImPossible);
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

    public ObservableList<Device> getDevices() {
        return devices.get();
    }

    public ListProperty<Device> devicesProperty() {
        return devices;
    }

    public void setDevices(List<? extends Device> devices) {
        this.devices.setValue(FXCollections.observableArrayList(devices));
    }

    public Device getSelectedDevice() {
        return selectedDevice.get();
    }

    public ObjectProperty<Device> selectedDeviceProperty() {
        return selectedDevice;
    }

    public void setSelectedDevice(Device selectedDevice) {
        this.selectedDevice.set(selectedDevice);
    }

    public boolean isDeletionDeviceImPossible() {
        return deletionDeviceImPossible.get();
    }

    public BooleanProperty deletionDeviceImPossibleProperty() {
        return deletionDeviceImPossible;
    }

    public boolean isUpdateDeviceImPossible() {
        return updateDeviceImPossible.get();
    }

    public BooleanProperty updateDeviceImPossibleProperty() {
        return updateDeviceImPossible;
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

    public boolean isHasPatientReadAccess() {
        return hasPatientReadAccess.get();
    }

    public BooleanProperty hasPatientReadAccessProperty() {
        return hasPatientReadAccess;
    }

    public boolean isHasVisitReadAccess() {
        return hasVisitReadAccess.get();
    }

    public BooleanProperty hasVisitReadAccessProperty() {
        return hasVisitReadAccess;
    }

    public boolean isHasUserReadAccess() {
        return hasUserReadAccess.get();
    }

    public BooleanProperty hasUserReadAccessProperty() {
        return hasUserReadAccess;
    }

    public boolean isHasRoleReadAccess() {
        return hasRoleReadAccess.get();
    }

    public boolean isHasDeviceReadAccess() {
        return hasDeviceReadAccess.get();
    }

    public BooleanProperty hasDeviceReadAccessProperty() {
        return hasDeviceReadAccess;
    }

    public void setHasDeviceReadAccess(boolean hasDeviceReadAccess) {
        this.hasDeviceReadAccess.set(hasDeviceReadAccess);
    }

    public BooleanProperty hasRoleReadAccessProperty() {
        return hasRoleReadAccess;
    }

    public boolean isHasPatientWriteAccess() {
        return hasPatientWriteAccess.get();
    }

    public BooleanProperty hasPatientWriteAccessProperty() {
        return hasPatientWriteAccess;
    }

    public boolean isHasVisitWriteAccess() {
        return hasVisitWriteAccess.get();
    }

    public BooleanProperty hasVisitWriteAccessProperty() {
        return hasVisitWriteAccess;
    }

    public boolean isHasUserWriteAccess() {
        return hasUserWriteAccess.get();
    }

    public BooleanProperty hasUserWriteAccessProperty() {
        return hasUserWriteAccess;
    }

    public boolean isHasRoleWriteAccess() {
        return hasRoleWriteAccess.get();
    }

    public BooleanProperty hasRoleWriteAccessProperty() {
        return hasRoleWriteAccess;
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

    public ObservableList<Role> getRoles() {
        return roles.get();
    }

    public ListProperty<Role> rolesProperty() {
        return roles;
    }

    public void setRoles(List<? extends Role> roles) {
        this.roles.set(FXCollections.observableArrayList(roles));
    }

    public Role getSelectedRole() {
        return selectedRole.get();
    }

    public ObjectProperty<Role> selectedRoleProperty() {
        return selectedRole;
    }

    public void setSelectedRole(Role selectedRole) {
        this.selectedRole.set(selectedRole);
    }

    public boolean isDeletionRoleImPossible() {
        return deletionRoleImPossible.get();
    }

    public BooleanProperty deletionRoleImPossibleProperty() {
        return deletionRoleImPossible;
    }

    public boolean isUpdateRoleImPossible() {
        return updateRoleImPossible.get();
    }

    public BooleanProperty updateRoleImPossibleProperty() {
        return updateRoleImPossible;
    }

    public User getUser() {
        return user.get();
    }

    public ObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
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
                ", roles=" + roles +
                ", selectedRole=" + selectedRole +
                ", deletionRoleImPossible=" + deletionRoleImPossible +
                ", updateRoleImPossible=" + updateRoleImPossible +
                '}';
    }

}
