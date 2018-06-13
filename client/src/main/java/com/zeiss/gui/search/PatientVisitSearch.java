package com.zeiss.gui.search;

import com.zeiss.gui.data.PatientService;
import com.zeiss.gui.data.PatientVisit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
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

    public PatientVisitSearch() {
    }

    public void showPatientSearchDialog(PatientService patientService, Stage parentStage, Locale locale){
        loadFxml(locale);
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

    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/gui/search/visit/PatientVisitSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
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
