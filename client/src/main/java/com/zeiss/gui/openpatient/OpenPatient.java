package com.zeiss.gui.openpatient;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.update.PatientUpdate;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;

public class OpenPatient {

    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField ageText;
    @FXML
    private DatePicker dobText;
    @FXML
    private Button edit;
    @FXML
    private TreeView treeview;

    private BorderPane borderPane;

    public OpenPatient() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showPatientDialog(PatientService patientService, Patient patient, Stage parentStage, Locale locale,
                                  Runnable runnable) {
        loadFxml(locale);
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Patient Delete All");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        firstNameText.textProperty().bindBidirectional(patient.firstNameProperty());
        lastNameText.textProperty().bindBidirectional(patient.lastNameProperty());
        dobText.valueProperty().bindBidirectional(patient.dobProperty());
        ageText.textProperty().bind(Bindings.createStringBinding(() -> {
            LocalDate value = dobText.getValue();
            if (value == null) {
                return null;
            }

            LocalDate now = LocalDate.now();

            return "" + Period.between(value, now).getYears();

        }, dobText.valueProperty()));
        patient.ageProperty().bind(ageText.textProperty());

        firstNameText.editableProperty().set(false);
        lastNameText.editableProperty().set(false);
        dobText.editableProperty().set(false);
        ageText.editableProperty().set(false);

        edit.setOnAction(event -> {
            new PatientUpdate().showPatientDialog(patientService, patient, runnable, parentStage, locale);
        });

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        dialog.showAndWait();

    }

    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/gui/OpenPatient.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
