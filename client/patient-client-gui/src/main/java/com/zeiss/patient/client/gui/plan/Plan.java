package com.zeiss.patient.client.gui.plan;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.zeiss.device.service.api.Device;
import com.zeiss.patient.client.gui.delete.PlanDeletion;
import com.zeiss.patient.client.gui.update.PlanUpdate;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.plan.service.api.PlanningUnit;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.stream.Collectors;

public class Plan implements Initializable {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private Button find;
    @FXML
    private TextField deviceName;
    @FXML
    private TextField host;
    @FXML
    private TextField port;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker planDate;
    @FXML
    private ListView<PatientTextFlow> patientList;
    @FXML
    private TableView<PlanningUnitWrapper> planTableView;

    private BorderPane borderPane;


    private TransientModel transientModel;
    @Inject
    private LocaleService localeService;
    @Inject
    private PatientService patientService;
    @Inject
    private PlanService planService;
    @Inject
    private Provider<PlanningUnit> planningUnitProvider;
    @Inject
    private Provider<PlanUpdate> planUpdateProvider;
    @Inject
    private Provider<PlanningUnitTimePicker> planningUnitTimePickerProvider;
    @Inject
    private Provider<PersistUnsavedPlanChanges> persistUnsavedPlanChangesProvider;
    private Stage parentStage;
    private Device device;

    @Inject
    public Plan(TransientModel transientModel) {
        this.transientModel = transientModel;
    }

    public Plan() {
    }

    public void showPlanDialog(Device device, Stage parentStage, Runnable runnable) {

        this.parentStage = parentStage;
        this.device = device;

        loadFxml();

        Dialog<String> dialog = new Dialog<>();

        dialog.initOwner(parentStage);

        dialog.setTitle("Patient Create or Update");

        dialog.getDialogPane().setPrefSize(1000, 800);

        deviceName.textProperty().bindBidirectional(device.deviceNameProperty());
        host.textProperty().bindBidirectional(device.hostProperty());
        port.textProperty().set(device.getPort().toString());


        save.setOnAction(event -> saveAction());
        cancel.setOnAction(event -> close(dialog, runnable));

        planTableView.getColumns().stream().map(patientTableColumn -> (TableColumn<PlanningUnitWrapper, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<PlanningUnitWrapper,
                        String>(patientTableColumn.getId())));

        planTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                transientModel.clearSelection();
            } else {
                transientModel.setSelectedPlanningUnit(newValue);
            }
        });

        delete.setOnAction(event -> PlanDeletion.planDeletionDeletionDialog(
                transientModel
        ));

        update.setOnAction(event -> planUpdateProvider.get().showPlanDialog(true, transientModel.getSelectedPlanningUnit(), parentStage));

        bindToModel();

        find.setOnAction(event -> findAction(patientService));

        if (planDate.getValue() == null) {
            disableOnDateSelection();
        }

        final BooleanProperty isListener = new SimpleBooleanProperty();
        planDate.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (isListener.get()) {
                return;
            }
            isListener.set(true);
            handleOnDateSelection(oldValue, newValue);
            isListener.set(false);
        });

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.showAndWait();

    }

    private void bindToModel() {
        delete.disableProperty().bind(transientModel.deletionImPossibleProperty());
        update.disableProperty().bind(transientModel.updateImPossibleProperty());

        save.disableProperty().bind((transientModel.newlyCreatedProperty().emptyProperty()).
                and(transientModel.updatedUnitsProperty().emptyProperty()).and(transientModel.deletedUnitsProperty().emptyProperty()));
    }

    private void findAction(PatientService patientService) {
        String firstNameTextFieldText = firstNameTextField.getText();
        String lastNameText = lastNameTextField.getText();
        List<Patient> patients = new ArrayList<>();
        if (!firstNameTextFieldText.isEmpty() && !lastNameText.isEmpty()) {
            patients.addAll(patientService.getPatientsByFirstNameAndLastName(firstNameTextFieldText, lastNameText));
        }
        if (!firstNameTextFieldText.isEmpty() && lastNameText.isEmpty()) {
            patients.addAll(patientService.getPatientsByFirstName(firstNameTextFieldText));
        }
        if (firstNameTextFieldText.isEmpty() && !lastNameText.isEmpty()) {
            patients.addAll(patientService.getPatientsByLastName(lastNameText));
        }
        patientList.setItems(FXCollections.observableArrayList(patients.stream().
                map(o -> {

                    PatientTextFlow textFlow = new PatientTextFlow(o);

                    String firstName = o.getFirstName().trim() + " ";
                    if (firstNameTextFieldText.isEmpty()) {
                        final Text firstText = new Text(firstName);
                        textFlow.getChildren().add(firstText);
                    } else {
                        extractTextFlowItems(firstNameTextFieldText, textFlow, firstName);
                    }
                    final Text emptyText = new Text(" ");
                    textFlow.getChildren().add(emptyText);
                    String lastName = o.getLastName().trim() + " ";
                    if (lastNameTextField.getText().isEmpty()) {
                        final Text lastText = new Text(lastName);
                        textFlow.getChildren().add(lastText);
                    } else {
                        extractTextFlowItems(lastNameTextField.getText(), textFlow, lastName);
                    }

                    return textFlow;
                }).collect(Collectors.toList())));

    }


    private void extractTextFlowItems(String nameTextFieldText, TextFlow textFlow, String nameText) {
        String[] nameSplit = nameText.split("(?i)" + nameTextFieldText);
        boolean firstOccurence = true;
        boolean capitalize = false;
        for (String splitValue : nameSplit) {
            if (!firstOccurence) {

                String s = nameTextFieldText.toLowerCase();
                if (capitalize) {
                    s = StringUtils.capitalize(s);
                    capitalize = false;
                }

                Text highlightedText = new Text(s);

                highlightedText.setFill(Color.BLUE);
                highlightedText.setStyle("-fx-font-weight: bold");
                textFlow.getChildren().add(highlightedText);
            } else {
                firstOccurence = false;
                capitalize = splitValue.isEmpty();
            }

            Text textToBeAdded = new Text(splitValue.isEmpty() ? " " : splitValue);
            textFlow.getChildren().add(textToBeAdded);

        }
    }

    @FXML
    private void handleDragDetected(MouseEvent event) {

        final MultipleSelectionModel<PatientTextFlow> selectionModel = patientList.getSelectionModel();

        if (!selectionModel.isEmpty()) {
            Dragboard startDragAndDrop = patientList.startDragAndDrop(TransferMode.ANY);
            ClipboardContent cb = new ClipboardContent();
            cb.putString(selectionModel.getSelectedItem().getPatient().getId());
            startDragAndDrop.setContent(cb);
            event.consume();
        }
    }

    @FXML
    private void handleDrop(DragEvent event) {
        String patientId = event.getDragboard().getString();

        PlanningUnit planningUnit = planningUnitProvider.get();
        Patient patient = patientService.getPatientsById(patientId);
        planningUnit.setId(UUID.randomUUID().toString());
        planningUnit.setPatient(patient);
        planningUnit.setFirstName(patient.getFirstName());
        planningUnit.setLastName(patient.getLastName());
        planningUnit.setDevice(device);
        planningUnit.setPlanDate(planDate.getValue());

        PlanningUnitWrapper planningUnitWrapper = new PlanningUnitWrapper(planningUnit, ChangeType.NEWLY_CREATED);

        planningUnitTimePickerProvider.get().showPlanDialog(false, planningUnitWrapper, parentStage);
        System.out.println(patientId);
    }

    @FXML
    private void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    private void handleOnDateSelection(LocalDate oldValue, LocalDate newValue) {

        if (!transientModel.getNewlyCreated().isEmpty() || !transientModel.getUpdatedUnits().isEmpty()) {
            persistUnsavedPlanChangesProvider.get().showPlanDialog(parentStage, planDate, oldValue, newValue, this::loadPlanTableViewItems);
        } else {
            clearAndLoadTable(newValue);
        }

    }

    private void clearAndLoadTable(LocalDate newValue) {
        clearFields();
        if (newValue == null) {
            disableOnDateSelection();
        } else {
            loadPlanTableViewItems(newValue);
        }
    }

    private void loadPlanTableViewItems() {
        clearAndLoadTable(planDate.getValue());
    }

    private void clearFields() {
        patientList.getItems().clear();
        planTableView.getItems().clear();
        firstNameTextField.clear();
        lastNameTextField.clear();
    }

    private void loadPlanTableViewItems(LocalDate newValue) {
        addAllUnchangedPlaningUnitsToModel(newValue);

        ListBinding<PlanningUnitWrapper> planningUnitListBinding = ConcatBindings.concat(transientModel.unchangedProperty(),
                transientModel.newlyCreatedProperty(), transientModel.updatedUnitsProperty());
        planTableView.itemsProperty().bind(planningUnitListBinding);
        enableOnDateSelection();
    }

    private void addAllUnchangedPlaningUnitsToModel(LocalDate newValue) {
        transientModel.clearModel();
        List<? extends PlanningUnit> planningUnits = planService.
                getPatientPlanningUnitsByDeviceAndDate(device.getDeviceName(), newValue);

        List<PlanningUnitWrapper> planningUnitWrappers = planningUnits.stream().map(o ->
                new PlanningUnitWrapper(o, ChangeType.UNCHANGED)).collect(Collectors.toList());
        transientModel.getUnchanged().addAll(planningUnitWrappers);
    }

    private void disableOnDateSelection() {
        clearFields();
        patientList.setDisable(true);
        planTableView.setDisable(true);
        firstNameTextField.setDisable(true);
        lastNameTextField.setDisable(true);
    }

    private void enableOnDateSelection() {
        patientList.setDisable(false);
        planTableView.setDisable(false);
        firstNameTextField.setDisable(false);
        lastNameTextField.setDisable(false);
    }


    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/Plan.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void saveAction() {

        transientModel.getDeletedUnits().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::delete);
        transientModel.getUpdatedUnits().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::update);
        transientModel.getNewlyCreated().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::create);


        clearFields();

        addAllUnchangedPlaningUnitsToModel(planDate.getValue());

    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }
}
