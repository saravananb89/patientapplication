package com.zeiss.patient.client.gui.generate;

import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientVisit;
import javafx.concurrent.Task;

import javax.inject.Inject;
import javax.inject.Provider;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataGenerationOutputTask extends Task<DataGenerationOutput> {

    private DataGenerationOutput output;
    private DataGenerationInputParameters input;

    @Inject
    private Provider<Patient> patientProvider;
    @Inject
    private Provider<PatientVisit> patientVisitProvider;

    public void setInput(DataGenerationInputParameters input) {
        this.input = input;
    }

    @Override
    protected DataGenerationOutput call() throws Exception {
        generatePatients(input);
        return output;
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Failed!");
    }

    private void generatePatients(DataGenerationInputParameters input) {

        List<PatientCSVObject> patientCSVObjects = processInputFile(input.getCsvFile().getPath());
        output = new DataGenerationOutput();
        int count = 0;
        for (PatientCSVObject patientCSVObject : patientCSVObjects) {
            Patient patient1 = patientProvider.get();
            patient1.setFirstName(patientCSVObject.getFirstName());
            patient1.setLastName(patientCSVObject.getLastName());
            GenerateRandomDate generateRandomDate = new GenerateRandomDate().invoke(input.getDobFrom(), input.getDobUntil());
            LocalDate now = generateRandomDate.getNow();
            LocalDate randomBirthDate = generateRandomDate.getRandomBirthDate();
            String age = getAge(now, randomBirthDate);
            String emailId = getEmailId(patientCSVObject, input.getEmailPattern());
            patient1.setAge(age);
            patient1.setEmail(emailId);
            patient1.dobProperty().set(randomBirthDate);

            output.getPatients().add(patient1);

            if (input.isGenerateVisits()) {
                List<PatientVisit> patientVisitList = new ArrayList<>();
                int visitCount = input.getVisitCount();
                IntStream.range(0, visitCount).forEach(value -> {
                    PatientVisit patientVisit = patientVisitProvider.get();
                    patientVisit.setVisitPatientFirstName(patientCSVObject.getFirstName());
                    patientVisit.setVisitPatientLastName(patientCSVObject.getLastName());
                    patientVisit.patientVisitDateProperty().set(new GenerateRandomDate().invoke(input.getVisitDateFrom(), input.getGetVisitDateUntil())
                            .getRandomBirthDate());
                    patientVisitList.add(patientVisit);

                });
                output.getVisits().addAll(patientVisitList);
                output.getPatientToVisitMap().put(patient1, patientVisitList);
            }
            count++;
            updateProgress(count, patientCSVObjects.size());
            updateMessage(count + " von " + patientCSVObjects.size() + " erstellt");
            if (isCancelled()) {
                return;
            }
        }

        System.out.println(output.getPatients() + "" + output.getVisits());
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

    class GenerateRandomDate {
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

    private Function<String, PatientCSVObject> mapToItem = (line) -> {
        String COMMA = ",";
        String[] p = line.split(COMMA);// a CSV has comma separated lines
        PatientCSVObject item = new PatientCSVObject();
        item.setFirstName(p[1]);//<-- this is the first column in the csv file
        item.setLastName(p[0]);
        //more initialization goes here
        return item;
    };
}
