package com.zeiss.patient.client.gui.generate;

import com.google.inject.Inject;
import com.zeiss.patient.service.api.Patient;
import com.zeiss.patient.service.api.PatientVisit;
import com.zeiss.patient.client.gui.localeservice.LocaleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ViewPreview {

    @FXML
    private TreeTableView treeTableView;
    @FXML
    private Button close;
    @Inject
    private LocaleService localeService;

    private BorderPane borderPane;

    public ViewPreview() {
    }

    public void showPatientDialog(Map<Patient, List<PatientVisit>> patient, Stage parentStage) {
        loadFxml();

        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("View Preview");

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(borderPane);

        close.setOnAction(event -> {
            close(dialog);
        });

        treeTableView.getColumns()
                .forEach(patientTableColumn -> ((TreeTableColumn) patientTableColumn)
                        .setCellValueFactory(new TreeItemPropertyValueFactory<Patient, String>(((TreeTableColumn) patientTableColumn)
                                .getId())));

        TreeItem<TreeViewItemWrapper> treeItem = new TreeItem<>();
        treeTableView.setRoot(treeItem);

        List<TreeItem<TreeViewItemWrapper>> treeItemList = patient.entrySet().stream().map(patientListEntry -> {
            TreeItem<TreeViewItemWrapper> child = new TreeItem<>();
            child.setValue(new PatientWrapper(patientListEntry.getKey()));
            List<TreeItem<TreeViewItemWrapper>> collect = patientListEntry.getValue().stream().map(patientVisit -> {
                TreeItem<TreeViewItemWrapper> childVisit = new TreeItem<>();
                childVisit.setValue(new PatientVisitWrapper(patientVisit));
                return childVisit;
            }).collect(Collectors.toList());
            child.getChildren().addAll(collect);
            return child;
        }).collect(Collectors.toList());

        treeItem.getChildren().addAll(treeItemList);

        dialog.showAndWait();
    }

    public void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
        System.out.println(dialog);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/ViewPreview.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
