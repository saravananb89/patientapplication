package com.zeiss.patient.client.gui;

import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.patient.client.gui.clear.PatientClear;
import com.zeiss.patient.client.gui.clear.PatientVisitClear;
import com.zeiss.patient.client.gui.create.*;
import com.zeiss.patient.client.gui.delete.*;
import com.zeiss.patient.client.gui.generate.GeneratePatient;
import com.zeiss.patient.client.gui.openpatient.OpenPatient;
import com.zeiss.patient.client.gui.plan.Plan;
import com.zeiss.patient.client.gui.search.*;
import com.zeiss.patient.client.gui.update.*;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;

import javax.inject.Inject;
import javax.inject.Provider;

public class PatientPresenter {

    private final PatientView patientView;
    private final PatientModel patientModel;

    private final PatientService patientService;
    private final UserService userService;
    private final RoleService roleService;
    private final DeviceService deviceService;

    @Inject
    private LocaleService localeService;
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
    private Provider<UserUpdate> userUpdateProvider;
    @Inject
    private Provider<RoleUpdate> roleUpdateProvider;
    @Inject
    private Provider<DeviceUpdate> deviceUpdateProvider;
    @Inject
    private Provider<PatientCreation> patientCreationProvider;
    @Inject
    private Provider<DeviceCreation> deviceCreationProvider;
    @Inject
    private Provider<UserCreation> userCreationProvider;
    @Inject
    private Provider<RoleCreation> roleCreationProvider;
    @Inject
    private Provider<PatientSearch> patientSearchProvider;
    @Inject
    private Provider<PatientVisitSearch> patientVisitSearchProvider;
    @Inject
    private Provider<UserSearch> userSearchProvider;
    @Inject
    private Provider<RoleSearch> roleSearchProvider;
    @Inject
    private Provider<DeviceSearch> deviceSearchProvider;
    @Inject
    private Provider<Plan> planProvider;
    @Inject
    private Provider<Patient> patientProvider;
    @Inject
    private Provider<User> userProvider;
    @Inject
    private Provider<Role> roleProvider;
    @Inject
    private Provider<Device> deviceProvider;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;

    @Inject
    public PatientPresenter(PatientView patientView, PatientService patientService, UserService userService,
                            RoleService roleService, DeviceService deviceService) {
        this.patientView = patientView;
        this.patientModel = new PatientModel();
        this.patientService = patientService;
        this.userService = userService;
        this.roleService = roleService;
        this.deviceService = deviceService;
    }

    public void loadPatientData() {
        patientModel.setPatients(patientService.getPatients());
    }

    public void loadPatientVisitData() {
        patientModel.setVisitPatients(patientService.getPatientVisits());
        System.out.println(patientModel.getVisitPatients());
    }

    public void loadUserData() {
        patientModel.setUsers(userService.getUsers());
        System.out.println(patientModel.getUsers());
    }

    public void loadRoleData() {
        patientModel.setRoles(roleService.getRoles());
        System.out.println(patientModel.getRoles());
    }

    public void loadDeviceData() {
        patientModel.setDevices(deviceService.getDevices());
        System.out.println(patientModel.getRoles());
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

    public void selectUser(User user) {
        patientModel.setSelectedUser(user);
    }

    public void selectRole(Role role) {
        patientModel.setSelectedRole(role);
    }

    public void selectDevice(Device device) {
        patientModel.setSelectedDevice(device);
    }

    public void clearVisitSelection() {
        patientModel.setSelectedVisitPatient(null);
    }

    public void clearUserSelection() {
        patientModel.setSelectedUser(null);
    }

    public void clearRoleSelection() {
        patientModel.setSelectedRole(null);
    }

    public void clearDeviceSelection() {
        patientModel.setSelectedDevice(null);
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

    public void createUserAction() {
        userCreationProvider.get().showUserDialog(false, userService, userProvider.get(), this::loadUserData, patientView.getStage());

    }

    public void deleteUserAction() {
        UserDeletion.showUserDeletionDialog(userService,
                patientModel.getSelectedUser(), this::loadUserData);
    }

    public void updateUserAction() {
        userUpdateProvider.get().showUserDialog(true, userService, patientModel.getSelectedUser(), this::loadUserData, patientView.getStage());

    }

    public void searchUserAction() {
        userSearchProvider.get().showUserSearchDialog(patientView.getStage());

    }

    public void createRoleAction() {
        roleCreationProvider.get().showRoleDialog(false, roleService, roleProvider.get(), this::loadRoleData, patientView.getStage());

    }

    public void deleteRoleAction() {
        RoleDeletion.showRoleDeletionDialog(roleService,
                patientModel.getSelectedRole(), this::loadRoleData);
    }

    public void updateRoleAction() {
        roleUpdateProvider.get().showRoleDialog(true, roleService, patientModel.getSelectedRole(), this::loadRoleData, patientView.getStage());

    }

    public void searchRoleAction() {
        roleSearchProvider.get().showRoleSearchDialog(patientView.getStage());

    }

    public void createDeviceAction() {
        deviceCreationProvider.get().showDeviceDialog(deviceProvider.get(), this::loadDeviceData,
                patientView.getStage());

    }

    public void deleteDeviceAction() {
        DeviceDeletion.showDeletionDeletionDialog(deviceService,
                patientModel.getSelectedDevice(), this::loadDeviceData);
    }

    public void updateDeviceAction() {
        deviceUpdateProvider.get().showDeviceDialog(patientModel.getSelectedDevice(),
                this::loadDeviceData, patientView.getStage());

    }

    public void searchDeviceAction() {
        deviceSearchProvider.get().showDeviceSearchDialog(patientView.getStage());
    }

    public void planeAction() {
        planProvider.get().showPlanDialog(patientModel.getSelectedDevice(), patientView.getStage(), this::loadDeviceData);
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
        patientModel.setUser(localeService.getLoggedInUser());
        patientView.bindToModel(patientModel);
        loadPatientData();
        loadPatientVisitData();
        loadUserData();
        loadRoleData();
        loadDeviceData();
    }

    public void openPatient() {
        openPatient.get().showPatientDialog(patientModel.getSelectedPatient(), patientView.getStage(), this::loadPatientData, patientModel);
    }
}
