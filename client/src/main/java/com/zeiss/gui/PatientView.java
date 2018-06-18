package com.zeiss.gui;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.data.PatientVisit;
import com.zeiss.gui.localeservice.LocaleService;
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

    @Inject
    private LocaleService localeService;

    @Inject
    private PatientService patientService;
    @Inject
    private PatientPresenter patientPresenter;

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

        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<Patient, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>(patientTableColumn.getId())));

        visitTableView.getColumns().stream().map(patientTableColumn -> (TableColumn<PatientVisit, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<PatientVisit,
                        String>(patientTableColumn.getId())));

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
        german.setOnAction(event -> Main.loadStage(getStage(), Locale.GERMAN, false)
        );
        english.setOnAction(event -> Main.loadStage(getStage(), Locale.ENGLISH, false));
        generateTestData.setOnAction(event -> openDialog(patientPresenter::generateAction));

        deleteAllPatient.setOnAction(event -> patientPresenter.clear());

        deleteAllPatientVisit.setOnAction(event -> patientPresenter.clearVisit());

        openPatient.setOnAction(event -> patientPresenter.openPatient());

        logout.setOnAction(event -> Main.loadLoginDialog(getStage()));
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
        visitTableView.itemsProperty().bind(patientModel.visitPatientsProperty());
        deleteButton.disableProperty().bind(patientModel.deletionImPossibleProperty());
        update.disableProperty().bind(patientModel.updateImPossibleProperty());
        deleteVisits.disableProperty().bind(patientModel.deletionVisitImPossibleProperty());
        updateVisits.disableProperty().bind(patientModel.updateVisitImPossibleProperty());
        openPatient.disableProperty().bind(patientModel.openPatientImPossibleProperty());
    }

    public Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

}
