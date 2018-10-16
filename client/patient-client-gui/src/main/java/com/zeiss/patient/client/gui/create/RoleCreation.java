package com.zeiss.patient.client.gui.create;

import com.google.inject.Inject;
import com.zeiss.role.service.api.Access;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.settings.service.api.LocaleService;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class RoleCreation extends TextFieldValidation {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private Slider patientTabAccessSlider;
    @FXML
    private Slider visitTabAccessSlider;
    @FXML
    private Slider userTabAccessSlider;
    @FXML
    private Slider roleTabAccessSlider;
    @FXML
    private Slider deviceTabAccessSlider;

    @FXML
    private TextField roleName;

    private VBox vbox;

    @Inject
    private RoleService roleService;
    @Inject
    private LocaleService localeService;

    public RoleCreation() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showRoleDialog(boolean update, RoleService roleService, Role role, Runnable runnable, Stage parentStage) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.getDialogPane().setPrefSize(900,500);

        dialog.setTitle("User Create or Update");

        roleName.textProperty().set(role.getRoleName());

        if (update) {
            roleName.setDisable(true);
        } else {
            role.roleNameProperty().bind(roleName.textProperty());
        }

        patientTabAccessSlider.valueProperty().set(role.getPatientAccess().getRepresentation());
        visitTabAccessSlider.valueProperty().set(role.getVisitAccess().getRepresentation());
        userTabAccessSlider.valueProperty().set(role.getUserAccess().getRepresentation());
        roleTabAccessSlider.valueProperty().set(role.getRoleAccess().getRepresentation());
        deviceTabAccessSlider.valueProperty().set(role.getDeviceAccess().getRepresentation());

        patientTabAccessSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                role.patientAccessProperty().set(Access.getAccess(newValue.intValue())));

        visitTabAccessSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                role.visitAccessProperty().set(Access.getAccess(newValue.intValue())));

        userTabAccessSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                role.userAccessProperty().set(Access.getAccess(newValue.intValue())));

        roleTabAccessSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                role.roleAccessProperty().set(Access.getAccess(newValue.intValue())));

        deviceTabAccessSlider.valueProperty().addListener((observableValue, oldValue, newValue) ->
                role.deviceAccessProperty().set(Access.getAccess(newValue.intValue())));

       /* patientTabAccessSlider.setShowTickLabels(true);
        patientTabAccessSlider.setShowTickMarks(true);

        patientTabAccessSlider.setMajorTickUnit(2);
        patientTabAccessSlider.setMinorTickCount(0);
        patientTabAccessSlider.setBlockIncrement(1);
        patientTabAccessSlider.setCursor(Cursor.OPEN_HAND);*/


        BooleanBinding validUserNameBinding = roleName.textProperty().isEmpty();

        configureTextFieldBinding(validUserNameBinding, roleName, "Role Name is required");

        save.disableProperty().bind(validUserNameBinding);

        save.setOnAction(event -> saveAction(roleService, role, dialog, runnable));
        cancel.setOnAction(event -> close(dialog, runnable));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(vbox);

        bindSliderLayout(patientTabAccessSlider);
        bindSliderLayout(userTabAccessSlider);
        bindSliderLayout(roleTabAccessSlider);
        bindSliderLayout(visitTabAccessSlider);
        bindSliderLayout(deviceTabAccessSlider);

        dialog.showAndWait();

    }

    public void bindSliderLayout(Slider slider) {
        slider.applyCss();
        slider.layout();
        Pane node = (Pane) slider.lookup(".thumb");
        Label label = new Label();

        ObjectBinding<String> objectBinding = Bindings.createObjectBinding(() -> {
            Access access = Access.getAccess(slider.valueProperty().intValue());
            return access.toString();
        }, slider.valueProperty());

        label.textProperty().bind(objectBinding);
        node.getChildren().add(label);
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/create/RoleCreate.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAction(RoleService roleService, Role role, Dialog<String> dialog, Runnable runnable) {
        save(roleService, role);
        close(dialog, runnable);
    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    protected void save(RoleService roleService, Role role) {
        roleService.create(role);
    }
}
