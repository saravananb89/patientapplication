package com.zeiss.patient.client.gui;

import com.google.inject.Provider;
import com.zeiss.device.service.api.Device;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.user.service.api.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PatientView {

    @FXML
    private TableView<Patient> tableView;
    @FXML
    private Button update;
    @FXML
    private Button deleteButton;
    @FXML
    private Button search;
    @FXML
    private Button create;
    @FXML
    private Label statusLabel;

    @FXML
    private TableView<PatientVisit> visitTableView;
    @FXML
    private Button updateVisits;
    @FXML
    private Button deleteVisits;
    @FXML
    private Button searchVisits;
    @FXML
    private Button createVisits;
    @FXML
    private Button openPatient;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab patientTab;
    @FXML
    private Tab visitTab;
    @FXML
    private Tab userTab;
    @FXML
    private Tab roleTab;
    @FXML
    private Tab deviceTab;

    @FXML
    private MenuItem exit;
    @FXML
    private MenuItem showPatients;
    @FXML
    private MenuItem showVisits;
    @FXML
    private MenuItem german;
    @FXML
    private MenuItem english;
    @FXML
    private MenuItem generateTestData;
    @FXML
    private MenuItem deleteAllPatient;
    @FXML
    private MenuItem deleteAllPatientVisit;
    @FXML
    private MenuItem logout;
    @FXML
    private TableView<User> userTableView;
    @FXML
    private Button updateUser;
    @FXML
    private Button deleteUser;
    @FXML
    private Button searchUser;
    @FXML
    private Button createUser;

    @FXML
    private TableView<Role> roleTableView;
    @FXML
    private Button updateRole;
    @FXML
    private Button deleteRole;
    @FXML
    private Button searchRole;
    @FXML
    private Button createRole;

    @FXML
    private TableView<Device> deviceTableView;
    @FXML
    private Button updateDevice;
    @FXML
    private Button deleteDevice;
    @FXML
    private Button searchDevice;
    @FXML
    private Button createDevice;
    @FXML
    private Button plan;


    @Inject
    private LocaleService localeService;

    @Inject
    private PatientService patientService;
    @Inject
    private RoleService roleService;
    @Inject
    private PatientPresenter patientPresenter;
    @Inject
    private GuiStarter guiStarter;
    @Inject
    private Provider<User> userProvider;

    private BorderPane root;

    public PatientView() {
    }

    public LocaleService getLocaleService() {
        return localeService;
    }

    public void buildGui(Locale locale, String status) {
        localeService.setLocale(locale);
        loadFxml();
        initGUI();
        this.patientPresenter.initialize();
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                patientPresenter.clearSelection();
            } else {
                patientPresenter.selectPatient(newValue);
            }
        });

        visitTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                patientPresenter.clearVisitSelection();
            } else {
                patientPresenter.selectPatientVisit(newValue);
            }
        });
        statusLabel.setText(status);
        userTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                patientPresenter.clearUserSelection();
            } else {
                patientPresenter.selectUser(newValue);
            }
        });

        roleTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                patientPresenter.clearRoleSelection();
            } else {
                patientPresenter.selectRole(newValue);
            }
        });

        deviceTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                patientPresenter.clearDeviceSelection();
            } else {
                patientPresenter.selectDevice(newValue);
            }
        });
    }

    public void opaqueDisable(boolean opaqueVisible) {
        opaqueLayer.setDisable(opaqueVisible);
        root.setDisable(opaqueVisible);
    }

    public Parent patientShow() {
        return root;
    }


    private Region opaqueLayer = new Region();

    @FXML
    public void initialize() {

    }

    private void initGUI() {


        opaqueLayer.setStyle("-fx-background-color: #00000044;");
        opaqueLayer.setVisible(false);

        create.setOnAction(event -> openDialog(patientPresenter::createAction));

        deleteButton.setOnAction(event -> patientPresenter.deleteAction());
        update.setOnAction(event -> openDialog(patientPresenter::updateAction));
        search.setOnAction(event -> openDialog(patientPresenter::searchPatient));

        createVisits.setOnAction(event -> openDialog(patientPresenter::createVisitAction));
        deleteVisits.setOnAction(event -> patientPresenter.deleteVisitAction());
        updateVisits.setOnAction(event -> openDialog(patientPresenter::updateVistAction));
        searchVisits.setOnAction(event -> openDialog(patientPresenter::searchVisitAction));

        createUser.setOnAction(event -> openDialog(patientPresenter::createUserAction));
        deleteUser.setOnAction(event -> patientPresenter.deleteUserAction());
        updateUser.setOnAction(event -> openDialog(patientPresenter::updateUserAction));
        searchUser.setOnAction(event -> openDialog(patientPresenter::searchUserAction));

        createRole.setOnAction(event -> openDialog(patientPresenter::createRoleAction));
        deleteRole.setOnAction(event -> patientPresenter.deleteRoleAction());
        updateRole.setOnAction(event -> openDialog(patientPresenter::updateRoleAction));
        searchRole.setOnAction(event -> openDialog(patientPresenter::searchRoleAction));

        createDevice.setOnAction(event -> openDialog(patientPresenter::createDeviceAction));
        deleteDevice.setOnAction(event -> patientPresenter.deleteDeviceAction());
        updateDevice.setOnAction(event -> openDialog(patientPresenter::updateDeviceAction));
        searchDevice.setOnAction(event -> openDialog(patientPresenter::searchDeviceAction));
        plan.setOnAction(event -> openDialog(patientPresenter::planeAction));

        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<Patient, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>(patientTableColumn.getId())));

        visitTableView.getColumns().stream().map(patientTableColumn -> (TableColumn<PatientVisit, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<PatientVisit,
                        String>(patientTableColumn.getId())));

        userTableView.getColumns().stream().map(patientTableColumn -> (TableColumn<User, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<User,
                        String>(patientTableColumn.getId())));

        roleTableView.getColumns().stream().map(roleTableColumn -> (TableColumn<Role, String>) roleTableColumn).
                forEach(roleTableColumn -> {
                    roleTableColumn.setCellValueFactory(new PropertyValueFactory<Role, String>
                            (roleTableColumn.getId()));
                });

        deviceTableView.getColumns().stream().map(roleTableColumn -> (TableColumn<Device, String>) roleTableColumn).
                forEach(roleTableColumn -> {
                    roleTableColumn.setCellValueFactory(new PropertyValueFactory<Device, String>
                            (roleTableColumn.getId()));
                });

        exit.setOnAction(event -> System.exit(0));

        showPatients.setOnAction(event -> {
            showPatients.disableProperty().set(true);
            showVisits.disableProperty().set(false);
            tabPane.getSelectionModel().select(0);
        });

        showVisits.setOnAction(event -> {
            showPatients.disableProperty().set(false);
            showVisits.disableProperty().set(true);
            tabPane.getSelectionModel().select(1);
        });
        german.setOnAction(event -> guiStarter.loadStage(getStage(), Locale.GERMAN, false)
        );
        english.setOnAction(event -> guiStarter.loadStage(getStage(), Locale.ENGLISH, false));
        generateTestData.setOnAction(event -> openDialog(patientPresenter::generateAction));

        deleteAllPatient.setOnAction(event -> patientPresenter.clear());

        deleteAllPatientVisit.setOnAction(event -> patientPresenter.clearVisit());

        openPatient.setOnAction(event -> patientPresenter.openPatient());

        logout.setOnAction(event -> guiStarter.loadLoginDialog(getStage()));

    }

    private void removeTab(Tab tab) {
        tabPane.getTabs().remove(tab);
    }

    private void openDialog(Runnable run) {
        root.setDisable(true);
        opaqueLayer.setVisible(true);
        run.run();
        opaqueLayer.setVisible(false);
        root.setDisable(false);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PatientView.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            root = (BorderPane) fxmlLoader.load();

            root.getChildren().add(opaqueLayer);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bindToModel(PatientModel patientModel) {
        tableView.itemsProperty().bind(patientModel.patientsProperty());
        deleteButton.disableProperty().bind(patientModel.deletionImPossibleProperty().or(patientModel.hasPatientWriteAccessProperty().not()));
        update.disableProperty().bind(patientModel.updateImPossibleProperty().or(patientModel.hasPatientWriteAccessProperty().not()));
        openPatient.disableProperty().bind(patientModel.openPatientImPossibleProperty());
        create.disableProperty().bind(patientModel.hasPatientWriteAccessProperty().not());

        visitTableView.itemsProperty().bind(patientModel.visitPatientsProperty());
        deleteVisits.disableProperty().bind(patientModel.deletionVisitImPossibleProperty().or(patientModel.hasVisitWriteAccessProperty().not()));
        updateVisits.disableProperty().bind(patientModel.updateVisitImPossibleProperty().or(patientModel.hasVisitWriteAccessProperty().not()));
        createVisits.disableProperty().bind(patientModel.hasVisitWriteAccessProperty().not());

        userTableView.itemsProperty().bind(patientModel.usersProperty());
        deleteUser.disableProperty().bind(patientModel.deletionUserImPossibleProperty().or(patientModel.hasUserWriteAccessProperty().not()));
        patientModel.updateUserImPossibleProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue + "" + newValue + "" + patientModel.isHasUserWriteAccess());
        });

        deleteUser.disableProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(oldValue + "" + newValue + "" + patientModel.isHasUserWriteAccess());
        });

        updateUser.disableProperty().bind(patientModel.updateUserImPossibleProperty().or(patientModel.hasUserWriteAccessProperty().not()));
        createUser.disableProperty().bind(patientModel.hasUserWriteAccessProperty().not());

        roleTableView.itemsProperty().bind(patientModel.rolesProperty());
        deleteRole.disableProperty().bind(patientModel.deletionRoleImPossibleProperty().or(patientModel.hasRoleWriteAccessProperty().not()));
        updateRole.disableProperty().bind(patientModel.updateRoleImPossibleProperty().or(patientModel.hasRoleWriteAccessProperty().not()));
        createRole.disableProperty().bind(patientModel.hasRoleWriteAccessProperty().not());

        deviceTableView.itemsProperty().bind(patientModel.devicesProperty());
        deleteDevice.disableProperty().bind(patientModel.deletionDeviceImPossibleProperty().or(patientModel.hasRoleWriteAccessProperty().not()));
        updateDevice.disableProperty().bind(patientModel.updateDeviceImPossibleProperty().or(patientModel.hasRoleWriteAccessProperty().not()));
        createDevice.disableProperty().bind(patientModel.hasRoleWriteAccessProperty().not());
        plan.disableProperty().bind(patientModel.planImPossibleProperty());

        if (!patientModel.isHasPatientReadAccess()) {
            tabPane.getTabs().remove(patientTab);
        }

        if (!patientModel.isHasVisitReadAccess()) {
            tabPane.getTabs().remove(visitTab);
        }
        if (!patientModel.isHasUserReadAccess()) {
            tabPane.getTabs().remove(userTab);
        }
        if (!patientModel.isHasRoleReadAccess()) {
            tabPane.getTabs().remove(roleTab);
        }
        if (!patientModel.isHasDeviceReadAccess()) {
            tabPane.getTabs().remove(deviceTab);
        }
    }

    public Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

}
