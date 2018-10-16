package com.zeiss.patient.client.gui.search;

import com.google.inject.Inject;
import com.zeiss.role.service.api.Role;
import com.zeiss.role.service.api.RoleService;
import com.zeiss.settings.service.api.LocaleService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class RoleSearch {

    @FXML
    private TableView<Role> tableView;
    @FXML
    private TextField roleNameTextField;
    @FXML
    private Button search;
    @FXML
    private Button close;
    @FXML
    private BorderPane borderPane;

    @Inject
    private RoleService roleService;
    @Inject
    private LocaleService localeService;

    public RoleSearch() {
    }

    public void showRoleSearchDialog(Stage parentStage) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.getDialogPane().setPrefSize(900,500);

        dialog.setTitle("Role Search");
        dialog.initOwner(parentStage);
        createTableView();
        search.setOnAction(event -> getRoleByRoleName(roleService, roleNameTextField));
        dialog.getDialogPane().contentProperty().setValue(borderPane);
        close.setOnAction(event -> close(dialog));
        dialog.showAndWait();
    }

    private void getRoleByRoleName(RoleService roleService, TextField roleName) {
        tableView.getItems().clear();
        Role role = roleService.getByRoleName(roleName.getText());
        tableView.getItems().addAll(role);
    }

    private void createTableView() {
        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<Role, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<Role, String>(patientTableColumn.getId())));
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/search/RoleSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
