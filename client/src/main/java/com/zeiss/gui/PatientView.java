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

    public void buildGui(Locale locale) {
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

        create.setOnAction(event ->
        {
            opaqueLayer.setVisible(true);
            root.setDisable(true);
            patientPresenter.createAction();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });

        deleteButton.setOnAction(event -> patientPresenter.deleteAction());
        update.setOnAction(event -> {
            root.setDisable(true);
            opaqueLayer.setVisible(true);
            patientPresenter.updateAction();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });
        search.setOnAction(event -> {
            root.setDisable(true);
            opaqueLayer.setVisible(true);
            patientPresenter.searchPatient();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });

        createVisits.setOnAction(event -> {
            root.setDisable(true);
            opaqueLayer.setVisible(true);
            patientPresenter.createVisitAction();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });
        deleteVisits.setOnAction(event -> patientPresenter.deleteVisitAction());
        updateVisits.setOnAction(event -> {
            root.setDisable(true);
            opaqueLayer.setVisible(true);
            patientPresenter.updateVistAction();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });
        searchVisits.setOnAction(event -> {
            root.setDisable(true);
            opaqueLayer.setVisible(true);
            patientPresenter.searchVisitAction();
            opaqueLayer.setVisible(false);
            root.setDisable(false);
        });

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
        german.setOnAction(event -> {
                    Main.loadStage(getStage(), Locale.GERMAN);
                }
        );
        english.setOnAction(event -> {
            Main.loadStage(getStage(), Locale.ENGLISH);
        });
        generateTestData.setOnAction(event -> patientPresenter.generateAction());
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
    }

    public Stage getStage() {
        return (Stage) root.getScene().getWindow();
    }

}
