package com.zeiss.gui.create;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class PatientCreation extends TextFieldValidation {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private TextField firstNameText;
    @FXML
    private TextField lastNameText;
    @FXML
    private TextField ageText;
    @FXML
    private DatePicker dobText;
    @FXML
    private TextField emailText;

    private VBox vbox;

    public PatientCreation() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showPatientDialog(PatientService patientService, Patient patient, Runnable runnable, Stage parentStage
    ,Locale locale){
        loadFxml(locale);
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Patient Create or Update");

        Pattern validEmailText = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9_-]*@([a-zA-Z0-9]+){1}(\\.[a-zA-Z0-9]+){1,2}");

        emailText.textProperty().bindBidirectional(patient.emailProperty());
        BooleanBinding validEmailBinding = Bindings.createBooleanBinding(() ->
                !validEmailText.matcher(emailText.getText()!=null? emailText.getText() : "").matches(), emailText.textProperty());

        firstNameText.textProperty().bindBidirectional(patient.firstNameProperty());
        lastNameText.textProperty().bindBidirectional(patient.lastNameProperty());
        dobText.valueProperty().bindBidirectional(patient.dobProperty());
        ageText.textProperty().bind(Bindings.createStringBinding(() -> {
            LocalDate value = dobText.getValue();
            if(value == null){
                return null;
            }

            LocalDate now = LocalDate.now();

            return ""+Period.between(value,now).getYears();

        },dobText.valueProperty()));
        patient.ageProperty().bind(ageText.textProperty());

        BooleanBinding validFirstNameBinding = firstNameText.textProperty().isEmpty();
        BooleanBinding validLastNameBinding = lastNameText.textProperty().isEmpty();
        BooleanBinding validDobBinding = dobText.valueProperty().isNull();

        configureTextFieldBinding(validFirstNameBinding, firstNameText, "First Name is required");
        configureTextFieldBinding(validLastNameBinding, lastNameText, "Last Name is required");
        configureTextFieldBinding(validDobBinding, dobText, "Dob is required");
        configureTextFieldBinding(validEmailBinding, emailText, "You must enter a valid email");

        save.disableProperty().bind(validFirstNameBinding.or(validLastNameBinding).
                or(validDobBinding).or(ageText.textProperty().isEmpty()).or(emailText.textProperty().isEmpty()).or(validEmailBinding));

        save.setOnAction(event -> saveAction(patientService, patient,dialog,runnable));
        cancel.setOnAction(event -> close(dialog,runnable));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(vbox);

        dialog.showAndWait();

    }



    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/gui/create/PatientCreate.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAction(PatientService patientService, Patient patient, Dialog<String> dialog, Runnable runnable) {
        save(patientService, patient);
        close(dialog,runnable);
    }

    private void close(Dialog<String> dialog,Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    protected void save(PatientService patientService, Patient patient) {
        patientService.create(patient);
    }
}
