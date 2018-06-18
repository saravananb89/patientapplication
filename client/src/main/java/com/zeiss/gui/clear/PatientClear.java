package com.zeiss.gui.clear;

import com.zeiss.gui.Main;
import com.zeiss.gui.data.PatientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class PatientClear {

    @FXML
    private Button cancel;
    @FXML
    private Button ok;

    private VBox vbox;

    public PatientClear() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showPatientDialog(PatientService patientService, Stage parentStage, Locale locale){
        loadFxml(locale);
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("Patient Delete All");

        ok.setOnAction(event -> clearAction(patientService,dialog,parentStage,locale));
        cancel.setOnAction(event -> close(dialog));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(vbox);

        dialog.showAndWait();

    }

    private void loadFxml(Locale locale) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/gui/clear/PatientClear.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", locale));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearAction(PatientService patientService, Dialog<String> dialog,Stage parentStage, Locale locale) {
        clear(patientService);
        Main.loadStage(parentStage,locale,false);
        close(dialog);
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    protected void clear(PatientService patientServicet) {
        patientServicet.clear();
    }
}
