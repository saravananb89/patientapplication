package com.zeiss.patient.client.gui.plan;

import com.google.inject.Inject;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.ListBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PersistUnsavedPlanChanges {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Button disgrad;
    @FXML
    private TableView<PlanningUnitWrapper> unSavedPlanTableView;

    private TransientModel transientModel;
    private BorderPane borderPane;

    @Inject
    private PlanService planService;
    @Inject
    private LocaleService localeService;

    @Inject
    public PersistUnsavedPlanChanges(TransientModel transientModel) {
        this.transientModel = transientModel;
    }

    public PersistUnsavedPlanChanges() {
    }

    public void showPlanDialog(Stage parentStage, DatePicker planDate, LocalDate oldValue, LocalDate newValue, Runnable runnable) {

        loadFxml();

        bindToModel();

        Dialog<String> dialog = new Dialog<>();

        dialog.initOwner(parentStage);

        dialog.setTitle("UnSaved Plan Changes");

        dialog.getDialogPane().setPrefSize(1000, 800);

        unSavedPlanTableView.getColumns().stream().map(patientTableColumn -> (TableColumn<PlanningUnitWrapper, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<PlanningUnitWrapper,
                        String>(patientTableColumn.getId())));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        disgrad.setOnAction(event -> disgard(planDate, newValue, dialog, runnable));

        cancel.setOnAction(event -> cancelUnsavedChanges(dialog));

        save.setOnAction(event -> save(planDate, newValue, dialog, runnable));

        dialog.showAndWait();
    }

    private void save(DatePicker planDate, LocalDate newValue, Dialog<String> dialog, Runnable runnable) {

        transientModel.getDeletedUnits().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::delete);
        transientModel.getUpdatedUnits().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::update);
        transientModel.getNewlyCreated().stream().map(PlanningUnitWrapper::getPlanningUnit).forEach(planService::create);

        transientModel.clearModel();
        planDate.valueProperty().set(newValue);
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    private void cancelUnsavedChanges(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void disgard(DatePicker planDate, LocalDate newValue, Dialog<String> dialog, Runnable runnable) {
        transientModel.clearModel();
        planDate.valueProperty().set(newValue);
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    private void bindToModel() {
        ListBinding<PlanningUnitWrapper> planningUnitListBinding = ConcatBindings.concat(transientModel.newlyCreatedProperty(),
                transientModel.updatedUnitsProperty(), transientModel.deletedUnitsProperty(), transientModel.newlyCreatedDeletedUnitsProperty());
        unSavedPlanTableView.itemsProperty().bind(planningUnitListBinding);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/UnSavedChanges.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
