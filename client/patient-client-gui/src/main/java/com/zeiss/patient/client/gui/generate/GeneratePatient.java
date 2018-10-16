package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.client.gui.GuiStarter;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.user.service.api.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratePatient {

    @FXML
    private TextField browserPathTextField;
    @FXML
    private Button load;
    @FXML
    private DatePicker betweenDateOfBirth;
    @FXML
    private DatePicker andDateOfBirth;
    @FXML
    private TextField email;
    @FXML
    private CheckBox generatePatientVisitCheckBox;
    @FXML
    private TextField visitPerPatient;
    @FXML
    private DatePicker visitDateBetween;
    @FXML
    private DatePicker visitDateAnd;
    @FXML
    private Button viewPreview;
    @FXML
    private Button patientGenerate;
    @FXML
    private Button cancel;

    @Inject
    private LocaleService localeService;

    @Inject
    private Provider<ViewPreview> viewPreviewProvider;
    @Inject
    private Provider<Patient> patientProvider;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;
    @Inject
    private GuiStarter guiStarter;

    @Inject
    private DataGenerationService dataGenerationService;
    @Inject
    private DataPersistenceService dataPersistenceService;
    @Inject
    private com.google.inject.Provider<User> userProvider;

    private final PatientService patientService;
    private final PatientGeneratedProgressBar patientGeneratedProgressBar;
    private BorderPane borderPane;

    private final List<Patient> patients = new ArrayList<>();
    private final List<PatientVisit> patientVisits = new ArrayList<>();
    private final Map<Patient, List<PatientVisit>> patientListMap = new HashMap<>();
    private File file;

    @Inject
    public GeneratePatient(PatientService patientService, PatientGeneratedProgressBar patientGeneratedProgressBar) {
        this.patientService = patientService;
        this.patientGeneratedProgressBar = patientGeneratedProgressBar;
        loadFxml();
    }

    public void showPatientDialog(Stage parentStage) {

        final ProgressBar progressBar = new ProgressBar(0);
        final ProgressIndicator progressIndicator = new ProgressIndicator(0);
        final Button progressClose = new Button("Cancel");

        final Label statusLabel = new Label();

        Dialog<String> dialog = new Dialog();

        Dialog<String> progressDialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Generate Patient Test Data");

        DataGenerationInputParameters input = new DataGenerationInputParameters();

        betweenDateOfBirth.setValue(LocalDate.of(1900, 1, 1));
        andDateOfBirth.setValue(LocalDate.now());
        email.setText("$firstName.$lastName@gmail.com");


        load.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);
                file = fileChooser.showOpenDialog(parentStage);
                if (file != null) {
                    browserPathTextField.setText(file.getPath());
                }
                System.out.println(file);

            }
        });

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        patientGenerate.setOnAction(event -> {
            doPatientGenerate(dialog, progressDialog, input);
            dataGenerationService.setOnSucceeded(t -> doDataGenerate(parentStage,
                    dialog, progressDialog));
        });

        progressClose.setOnAction(event -> {
            patientGenerate.setDisable(false);
            dataGenerationService.cancel();
            dataPersistenceService.cancel();
            progressBar.progressProperty().unbind();
            progressIndicator.progressProperty().unbind();
            statusLabel.textProperty().unbind();
            //
            progressBar.setProgress(0);
            progressIndicator.setProgress(0);
            System.out.println("cancelled.");
            progressDialog.setResult("True");
            progressDialog.close();
        });

        visitPerPatient.disableProperty().set(true);
        visitDateBetween.disableProperty().set(true);
        visitDateAnd.disableProperty().set(true);

        generatePatientVisitCheckBox.setOnAction(event -> {
            if (generatePatientVisitCheckBox.isSelected()) {
                visitPerPatient.setText("1");
                visitDateBetween.setValue(LocalDate.of(1900, 1, 1));
                visitDateAnd.setValue(LocalDate.now());
                visitPerPatient.disableProperty().set(false);
                visitDateBetween.disableProperty().set(false);
                visitDateAnd.disableProperty().set(false);
            } else {
                visitPerPatient.disableProperty().set(true);
                visitDateBetween.disableProperty().set(true);
                visitDateAnd.disableProperty().set(true);
            }
        });

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        cancel.setOnAction(event -> {
           /* patientGenerate.setDisable(false);
            cancel.setDisable(true);*/
            // copyWorker.cancel(true);
            close(dialog);
        });

        viewPreview.setOnAction((ActionEvent event) -> {
            patientGenerate.setDisable(true);
            progressBar.setProgress(0);
            progressIndicator.setProgress(0);
            cancel.setDisable(false);
            setInputParameters(input);
            dataGenerationService.setInput(input);

            progressBar.progressProperty().unbind();
            progressBar.progressProperty().bind(dataGenerationService.progressProperty());

            progressIndicator.progressProperty().unbind();

            progressIndicator.progressProperty().bind(dataGenerationService.progressProperty());

            // Unbind text property for Label.
            statusLabel.textProperty().unbind();

            // Bind the text property of Label
            // with message property of Task
            statusLabel.textProperty().bind(dataGenerationService.messageProperty());


            dataGenerationService.setOnSucceeded(t -> {
                DataGenerationOutput output = dataGenerationService.getValue();

                progressBar.progressProperty().unbind();
                progressIndicator.progressProperty().unbind();
                statusLabel.textProperty().unbind();
                progressDialog.setResult("True");
                progressDialog.close();
                     /*   dialog.setResult("True");
                        dialog.close();*/
                patientGenerate.setDisable(false);
                viewPreviewProvider.get().showPatientDialog(output.getPatientToVisitMap(), parentStage);
                dataGenerationService.reset();
            });
            progressDialog.getDialogPane().setContent(patientGeneratedProgressBar.getParent());
            dataGenerationService.start();
        });
        dialog.showAndWait();
    }

    public void doPatientGenerate(Dialog<String> dialog, Dialog<String> progressDialog, DataGenerationInputParameters input) {
        progressDialog.initOwner(dialog.getDialogPane().getContent().getScene().getWindow());

        progressDialog.setTitle("Generating Patient");

        setInputParameters(input);

        patientGenerate.setDisable(true);
        dataGenerationService.setInput(input);
        patientGeneratedProgressBar.unbind();
        patientGeneratedProgressBar.bind(dataGenerationService.progressProperty(), dataGenerationService.messageProperty());
        progressDialog.getDialogPane().setContent(patientGeneratedProgressBar.getParent());
        progressDialog.show();
        dataGenerationService.start();
    }

    public void doDataGenerate(Stage parentStage, Dialog<String> dialog, Dialog<String> progressDialog) {
        DataGenerationOutput output = (DataGenerationOutput) dataGenerationService.getValue();
        dataPersistenceService.setOutput(output);
        patientGeneratedProgressBar.unbind();
        patientGeneratedProgressBar.bind(dataPersistenceService.progressProperty(), dataPersistenceService.messageProperty());
        dataPersistenceService.setOnSucceeded(c -> {
            patientGeneratedProgressBar.unbind();
            progressDialog.setResult("True");
            progressDialog.close();
            dataPersistenceService.reset();
            persistPatients(dialog, parentStage, localeService);
        });
        dataPersistenceService.start();
        dataGenerationService.reset();
    }

    public void setInputParameters(DataGenerationInputParameters input) {
        input.setCsvFile(file);
        input.setDobFrom(betweenDateOfBirth.getValue());
        input.setDobUntil(andDateOfBirth.getValue());
        input.setEmailPattern(email.getText());
        input.setGenerateVisits(generatePatientVisitCheckBox.isSelected());
        input.setVisitCount(Integer.parseInt(visitPerPatient.getText()));
        input.setVisitDateFrom(visitDateBetween.getValue());
        input.setGetVisitDateUntil(visitDateAnd.getValue());
    }

    private void persistPatients(Dialog dialog, Stage parentStage, LocaleService localeService) {
        guiStarter.loadStage(parentStage, localeService.getLocale(), false);
        close(dialog);
    }


    public void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
        System.out.println(dialog);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/generatepatient/GeneratePatient.fxml"));
            fxmlLoader.setController(this);
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
