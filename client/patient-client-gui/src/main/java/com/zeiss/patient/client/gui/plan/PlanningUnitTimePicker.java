package com.zeiss.patient.client.gui.plan;

import com.google.inject.Inject;
import com.jfoenix.controls.JFXTimePicker;
import com.zeiss.plan.service.api.PlanService;
import com.zeiss.plan.service.api.PlanningUnit;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableBooleanValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ResourceBundle;


public class PlanningUnitTimePicker {

    private BorderPane borderPane;
    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Label errorMessage;
    @FXML
    private JFXTimePicker jfxTimePicker;

    @Inject
    private TransientModel transientModel;
    @Inject
    private LocaleService localeService;
    @Inject
    private PlanService planService;

    private boolean update;

    public void showPlanDialog(boolean update, PlanningUnitWrapper planningUnitWrapper, Stage parentStage) {

        loadFxml();

        this.update = update;

        Dialog<String> dialog = new Dialog<>();

        dialog.initOwner(parentStage);

        dialog.setTitle("Planning Unit TimePicker");

        dialog.getDialogPane().setPrefSize(500, 500);

        if (planningUnitWrapper.getPlanTime() != null)
            jfxTimePicker.valueProperty().set(LocalTime.parse(planningUnitWrapper.getPlanTime()));

        save.setOnAction(event -> saveAction(planningUnitWrapper, dialog));

        cancel.setOnAction(event -> close(dialog));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        errorMessage.setTextFill(Color.RED);

        getTimeAvailable(planningUnitWrapper);

        save.disableProperty().bind(jfxTimePicker.valueProperty().isNull().or(isInvalid(planningUnitWrapper)));

        dialog.showAndWait();

    }

    protected TextField getEditor() {
        return jfxTimePicker.getEditor();
    }

    private ObservableBooleanValue isInvalid(PlanningUnit planningUnit) {
        return Bindings.createBooleanBinding(() -> getTimeAvailable(planningUnit), getEditor().textProperty());
    }

    private Boolean getTimeAvailable(PlanningUnit planningUnit) {
        final String timeValue = jfxTimePicker.getValue() != null ? jfxTimePicker.getValue().toString() : "";
        return checkForTimeValue(planningUnit, timeValue);
    }

    private Boolean checkForTimeValue(PlanningUnit planningUnit, String timeValue) {
        if (!timeValue.equals("")) {
            boolean timeAvailable = transientModel.isTimeAvailable(planningUnit.getDevice().getDeviceName(), planningUnit.getPlanDate(), timeValue);
            if (timeAvailable) {
                setErrorMessage(timeValue);
                return true;
            }
            errorMessage.setVisible(false);
            return false;
        }
        setErrorMessage(timeValue);
        return true;
    }

    private void setErrorMessage(String timeValue) {
        errorMessage.setVisible(true);
        errorMessage.setText(Messages.getString("error.plantime.message", timeValue));
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void saveAction(PlanningUnitWrapper planningUnitWrapper, Dialog<String> dialog) {
        save(planningUnitWrapper);
        close(dialog);
    }

    private void save(PlanningUnitWrapper planningUnitWrapper) {
        final String timeValue = jfxTimePicker.getValue() != null ? jfxTimePicker.getValue().toString() : "";
        if (!timeValue.equals("")) {
            if (!transientModel.isTimeAvailable(planningUnitWrapper.getDevice().getDeviceName(), planningUnitWrapper.getPlanDate(), timeValue)) {
                planningUnitWrapper.setPlanTime(timeValue);

                if (update) {
                    if (planningUnitWrapper.getChangeType() == ChangeType.UNCHANGED) {
                        transientModel.getUnchanged().remove(planningUnitWrapper);
                        transientModel.getUpdatedUnits().addAll(planningUnitWrapper);
                        planningUnitWrapper.setChangeType(ChangeType.UPDATED);
                    }
                } else {
                    transientModel.getNewlyCreated().addAll(planningUnitWrapper);
                }

            } else {
                setErrorMessage(timeValue);
            }
        }
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/PlanningUnitTimePicker.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
