package com.zeiss.patient.client.gui.create;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class PatientVisitCreation {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private DatePicker visitDateTextField;

    private VBox vbox;

    public PatientVisitCreation() {
    }

    public void showPatientDialog(PatientService patientService, PatientVisit patientVisit, Runnable runnable, Stage parentStage,
                                  Locale locale) {
        loadFxml(locale);
        Dialog<String> dialog = new Dialog();

        dialog.setTitle("Patient Visit Create or Update");
        dialog.initOwner(parentStage);

        firstNameText.textProperty().bindBidirectional(patientVisit.visitPatientFirstNameProperty());
        lastNameText.textProperty().bindBidirectional(patientVisit.visitPatientLastNameProperty());
        visitDateTextField.valueProperty().bindBidirectional(patientVisit.patientVisitDateProperty());

        BooleanBinding validFirstNameBinding = firstNameText.textProperty().isEmpty();
        BooleanBinding validLastNameBinding = lastNameText.textProperty().isEmpty();
        BooleanBinding validVisitDateBinding = visitDateTextField.valueProperty().isNull();
        BooleanProperty patientExist = new SimpleBooleanProperty();

        TextFieldValidation.configureTextFieldBinding(validFirstNameBinding, patientExist.not(), firstNameText, "First Name is required", "Patient not Exist");
        TextFieldValidation.configureTextFieldBinding(validLastNameBinding, patientExist.not(), lastNameText, "Last Name is required", "Patient not Exist");
        TextFieldValidation.configureTextFieldBinding(validVisitDateBinding, visitDateTextField, "VisitDate is required");


        save.disableProperty().bind(validFirstNameBinding.or(validLastNameBinding).or(validVisitDateBinding).
                or(patientExist.not()));


        firstNameText.textProperty().addListener((n, o, v) -> {
            if (!v.isEmpty() && lastNameText.textProperty().isNotEmpty().get()) {
                List<Patient> patientsVisitListByFirstNameAndLastName = patientService.
                        getPatientsByFirstNameAndLastName(v, lastNameText.getText());
                Optional<Patient> patient = patientsVisitListByFirstNameAndLastName.stream().findFirst().filter(p -> p.getFirstName().equals(v)
                        && p.getLastName().equals(lastNameText.getText()));
                if (patient.isPresent()) {
                    patientExist.setValue(true);
                } else {
                    patientExist.setValue(false);
                }
            } else if (v.isEmpty()) {
                patientExist.set(false);
            }
        });


        lastNameText.textProperty().addListener((n, o, v) -> {
            if (!v.isEmpty() && firstNameText.textProperty().isNotEmpty().get()) {
                List<Patient> patientsListByFirstNameAndLastName = patientService.
                        getPatientsByFirstNameAndLastName(firstNameText.getText(), v);
                Optional<Patient> patient = patientsListByFirstNameAndLastName.stream().findFirst().
                        filter(p -> p.getLastName().equals(v)
                                && p.getFirstName().equals(firstNameText.getText()));
                if (patient.isPresent()) {
                    patientExist.setValue(true);
                } else {
                    patientExist.setValue(false);
                }
            } else if (v.isEmpty()) {
                patientExist.set(false);
            }
        });

        save.setOnAction(event -> saveAction(patientService, patientVisit, dialog, runnable));
        cancel.setOnAction(event -> close(dialog, runnable));


        dialog.getDialogPane().contentProperty().setValue(vbox);

        dialog.showAndWait();

    }

    private void saveAction(PatientService patientService, PatientVisit patientVisit, Dialog<String> dialog, Runnable runnable) {
        save(patientService, patientVisit);
        close(dialog, runnable);
    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/create/PatientVisitCreate.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void save(PatientService patientService, PatientVisit patientVisit) {
        patientService.createVisit(patientVisit);
    }
}


