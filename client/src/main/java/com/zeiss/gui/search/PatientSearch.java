package com.zeiss.gui.search;

import com.zeiss.gui.data.Patient;
import com.zeiss.gui.data.PatientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PatientSearch {

    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TextField lastNameText;
    @FXML
    private Button search;
    @FXML
    private Button close;
    @FXML
    private BorderPane borderPane;

    public PatientSearch() {
    }

    public void showPatientSearchDialog(PatientService patientService, Stage parentStage, Locale locale){
        loadFxml(locale);
        Dialog<String> dialog = new Dialog();

        dialog.setTitle("Patient Search");
        dialog.initOwner(parentStage);
        createTableView();
        search.setOnAction(event -> getPatientsByLastName(patientService, lastNameText));
        dialog.getDialogPane().contentProperty().setValue(borderPane);
        close.setOnAction(event -> close(dialog));
        dialog.showAndWait();
    }

    private void getPatientsByLastName(PatientService patientService, TextField lastNameText) {
        tableView.getItems().clear();
        tableView.getItems().addAll(patientService.getPatientsByLastName(lastNameText.getText()));
        }

    private void createTableView()
    {
        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<Patient, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<Patient, String>(patientTableColumn.getId())));
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/gui/search/PatientSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
