package com.zeiss.patient.client.gui.search;

import com.google.inject.Inject;
import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.settings.service.api.LocaleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class DeviceSearch {
    @FXML
    private TableView<Device> tableView;
    @FXML
    private TextField deviceNameText;
    @FXML
    private Button search;
    @FXML
    private Button close;
    @FXML
    private BorderPane borderPane;

    @Inject
    private DeviceService deviceService;
    @Inject
    private LocaleService localeService;

    public DeviceSearch() {
    }

    public void showDeviceSearchDialog(Stage parentStage) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.setTitle("Device Search");
        dialog.initOwner(parentStage);
        createTableView();
        search.setOnAction(event -> getDevisecsByDeviceName(deviceService, deviceNameText));
        dialog.getDialogPane().contentProperty().setValue(borderPane);
        close.setOnAction(event -> close(dialog));
        dialog.showAndWait();
    }

    private void getDevisecsByDeviceName(DeviceService deviceService, TextField deviceNameText) {
        tableView.getItems().clear();
        tableView.getItems().addAll(deviceService.getByDeviceName(deviceNameText.getText()));
    }

    private void createTableView() {
        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<Device, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<Device, String>(patientTableColumn.getId())));
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/search/DeviceSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
