package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.client.gui.GuiStarter;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.client.gui.localeservice.LocaleService;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private GuiStarter guiStarter;

    private final PatientService patientService;
    private BorderPane borderPane;

    private final List<Patient> patients = new ArrayList<>();
    private final List<PatientVisit> patientVisits = new ArrayList<>();
    private final Map<Patient, List<PatientVisit>> patientListMap = new HashMap<>();
    private File file;

    @Inject
    public GeneratePatient(PatientService patientService) {
        this.patientService = patientService;
        loadFxml();
    }

    public void showPatientDialog(Stage parentStage) {

        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Generate Patient Test Data");

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

        patientGenerate.setOnAction(event -> {
            generatePatients(file);
            persistPatients(dialog, parentStage, localeService);
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
            close(dialog);
        });

        viewPreview.setOnAction(event -> {
            generatePatients(file);
            viewPreviewProvider.get().showPatientDialog(patientListMap, parentStage);
        });
        dialog.showAndWait();

    }

    private void persistPatients(Dialog dialog, Stage parentStage, LocaleService localeService) {
        patients.forEach(patientService::create);
        patientVisits.forEach(patientService::createVisit);
        guiStarter.loadStage(parentStage, localeService.getLocale(), false);
        close(dialog);
    }

    private void generatePatients(File file) {
        List<PatientCSVObject> patientCSVObjects = processInputFile(file.getPath());

        patientCSVObjects.forEach(patientCSVObject -> {
            Patient patient1 = new Patient();
            patient1.setFirstName(patientCSVObject.getFirstName());
            patient1.setLastName(patientCSVObject.getLastName());
            GenerateRandomDate generateRandomDate = new GenerateRandomDate().invoke(betweenDateOfBirth.getValue(), andDateOfBirth.getValue());
            LocalDate now = generateRandomDate.getNow();
            LocalDate randomBirthDate = generateRandomDate.getRandomBirthDate();
            String age = getAge(now, randomBirthDate);
            String emailId = getEmailId(patientCSVObject, email.getText());
            patient1.setAge(age);
            patient1.setEmail(emailId);
            patient1.dobProperty().set(randomBirthDate);

            patients.add(patient1);

            if (generatePatientVisitCheckBox.isSelected()) {
                List<PatientVisit> patientVisitList = new ArrayList<>();
                int visitCount = Integer.parseInt(visitPerPatient.getText());
                IntStream.range(0, visitCount).forEach(value -> {
                    PatientVisit patientVisit = new PatientVisit();
                    patientVisit.setVisitPatientFirstName(patientCSVObject.getFirstName());
                    patientVisit.setVisitPatientLastName(patientCSVObject.getLastName());
                    patientVisit.patientVisitDateProperty().set(new GenerateRandomDate().invoke(visitDateBetween.getValue(), visitDateAnd.getValue())
                            .getRandomBirthDate());
                    patientVisitList.add(patientVisit);

                });
                patientVisits.addAll(patientVisitList);
                patientListMap.put(patient1, patientVisitList);
            }

        });

        System.out.println(patients + "" + patientVisits);
    }

    private String getAge(LocalDate now, LocalDate randomBirthDate) {
        return "" + Period.between(randomBirthDate, now).getYears();
    }

    private String getEmailId(PatientCSVObject patientCSVObject, String emailPattern) {
        return emailPattern.replaceAll("\\$firstName", patientCSVObject.getFirstName()).
                replaceAll("\\$lastName", patientCSVObject.getLastName()).trim();
    }

    private List<PatientCSVObject> processInputFile(String inputFilePath) {
        List<PatientCSVObject> inputList = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new FileReader(inputFilePath))) {
            // skip the header of the csv
            inputList = br.lines().map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputList;
    }

    private Function<String, PatientCSVObject> mapToItem = (line) -> {
        String COMMA = ",";
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        PatientCSVObject item = new PatientCSVObject();
        item.setFirstName(p[1]);//<-- this is the first column in the csv file
        item.setLastName(p[0]);
        //more initialization goes here
        return item;
    };

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

    private class GenerateRandomDate {
        private LocalDate now;
        private LocalDate randomBirthDate;

        public LocalDate getNow() {
            return now;
        }

        public LocalDate getRandomBirthDate() {
            return randomBirthDate;
        }

        public GenerateRandomDate invoke(LocalDate minDate, LocalDate maxDate) {
            now = LocalDate.now();
            Random random = new Random();
            int minDay = (int) minDate.toEpochDay();
            int maxDay = (int) maxDate.toEpochDay();
            long randomDay = (long) minDay + random.nextInt(maxDay - minDay);

            randomBirthDate = LocalDate.ofEpochDay(randomDay);
            return this;
        }
    }
}
