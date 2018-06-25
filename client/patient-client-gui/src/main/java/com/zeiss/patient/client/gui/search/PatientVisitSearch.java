package com.zeiss.patient.client.gui.search;

import com.google.inject.Inject;
import com.zeiss.patient.service.api.PatientService;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.client.gui.localeservice.LocaleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class PatientVisitSearch {

    @FXML
    private TableView<PatientVisit> tableView;
    @FXML
    private TextField lastNameText;
    @FXML
    private Button search;
    @FXML
    private Button close;
    @FXML
    private BorderPane borderPane;

    @Inject
    private PatientService patientService;
    @Inject
    private LocaleService localeService;

    public PatientVisitSearch() {
    }

    public void showPatientSearchDialog( Stage parentStage){
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.setTitle("Patient Visit Search");
        dialog.initOwner(parentStage);
        createTableView();
        search.setOnAction(event -> getPatientsByLastName(patientService, lastNameText));
        dialog.getDialogPane().contentProperty().setValue(borderPane);
        close.setOnAction(event -> close(dialog));
        dialog.showAndWait();
    }

    private void getPatientsByLastName(PatientService patientService, TextField lastNameText) {
        tableView.getItems().clear();
        tableView.getItems().addAll(patientService.getVisitPatientsByLastName(lastNameText.getText()));
    }

    private void createTableView()
    {
        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<PatientVisit, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<PatientVisit,
                        String>(patientTableColumn.getId())));
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/search/visit/PatientVisitSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }
}
