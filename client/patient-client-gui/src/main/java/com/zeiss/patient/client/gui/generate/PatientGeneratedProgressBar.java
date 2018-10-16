package com.zeiss.patient.client.gui.generate;

import com.google.inject.Inject;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ResourceBundle;

public class PatientGeneratedProgressBar {
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button progressClose;

    @Inject
    private LocaleService localeService;

    private VBox vBox;

    public PatientGeneratedProgressBar() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public Parent getParent() {
        if (vBox == null) {
            loadFxml();
        }
        return vBox;
    }

    public void bind(ReadOnlyDoubleProperty progressProperty, ReadOnlyStringProperty messageProperty) {
        getParent();
        progressBar.progressProperty().bind(progressProperty);
        progressIndicator.progressProperty().bind(progressProperty);
        statusLabel.textProperty().bind(messageProperty);
    }

    public void unbind() {
        getParent();
        progressBar.progressProperty().unbind();
        progressIndicator.progressProperty().unbind();
        statusLabel.textProperty().unbind();
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getClassLoader().getResource("com/zeiss/patient/client/gui/generatepatient/ProgressBar.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            vBox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
