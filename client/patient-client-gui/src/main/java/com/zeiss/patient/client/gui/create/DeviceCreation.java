package com.zeiss.patient.client.gui.create;

import com.google.inject.Inject;
import com.zeiss.device.service.api.Device;
import com.zeiss.device.service.api.DeviceService;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class DeviceCreation extends TextFieldValidation {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private TextField deviceNameText;
    @FXML
    private TextField hostText;
    @FXML
    Spinner<Integer> mySpinner;

    private VBox vbox;

    @Inject
    private DeviceService deviceService;
    @Inject
    private LocaleService localeService;

    public DeviceCreation() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showDeviceDialog(Device device, Runnable runnable, Stage parentStage
    ) {
        loadFxml();
        Dialog<String> dialog = new Dialog<>();

        dialog.initOwner(parentStage);

        dialog.setTitle("Patient Create or Update");

        deviceNameText.textProperty().bindBidirectional(device.deviceNameProperty());
        hostText.textProperty().bindBidirectional(device.hostProperty());


        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                // NumberFormat evaluates the beginning of the text
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    // reject parsing the complete text failed
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> formatter = new TextFormatter<Integer>(
                new IntegerStringConverter(), 0, filter);

        SpinnerValueFactory factory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, 9999);
        mySpinner.setValueFactory(factory);
        mySpinner.getEditor().setTextFormatter(formatter);

        mySpinner.getValueFactory().setValue(device.getPort());

        factory.valueProperty().bindBidirectional(formatter.valueProperty());

        mySpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            System.out.println(newValue);
        });

        mySpinner.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            // let the user clear the field without complaining
            if (!newValue.isEmpty()) {
                Integer value = mySpinner.getValue();
                try {
                    value = mySpinner.getValueFactory().getConverter().fromString(newValue);
                } catch (Exception e) { /* user typed an illegal character */ }
                mySpinner.getValueFactory().setValue(value);
            }
        });

        device.portProperty().bind(mySpinner.valueProperty());
        BooleanBinding validDeviceNameBinding = deviceNameText.textProperty().isEmpty();
        BooleanBinding validHostBinding = hostText.textProperty().isEmpty();

        configureTextFieldBinding(validDeviceNameBinding, deviceNameText, "Device Name is required");
        configureTextFieldBinding(validHostBinding, hostText, "Host is required");

        save.disableProperty().bind(validDeviceNameBinding.or(validHostBinding));

        save.setOnAction(event -> saveAction(deviceService, device, dialog, runnable));
        cancel.setOnAction(event -> close(dialog, runnable));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(vbox);

        dialog.showAndWait();

    }


    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/create/DeviceCreate.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAction(DeviceService deviceService, Device device, Dialog<String> dialog, Runnable runnable) {
        save(deviceService, device);
        close(dialog, runnable);
    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    protected void save(DeviceService deviceService, Device device) {

        deviceService.create(device);
    }

}
