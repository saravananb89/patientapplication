package com.zeiss.patient.client.gui.search;

import com.google.inject.Inject;
import com.zeiss.settings.service.api.LocaleService;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class UserSearch {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TextField userNameTextField;
    @FXML
    private Button search;
    @FXML
    private Button close;
    @FXML
    private BorderPane borderPane;

    @Inject
    private UserService userService;
    @Inject
    private LocaleService localeService;

    public UserSearch() {
    }

    public void showUserSearchDialog(Stage parentStage){
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.setTitle("User Search");
        dialog.initOwner(parentStage);
        createTableView();
        search.setOnAction(event -> getUsersByUserName(userService, userNameTextField));
        dialog.getDialogPane().contentProperty().setValue(borderPane);
        close.setOnAction(event -> close(dialog));
        dialog.showAndWait();
    }

    private void getUsersByUserName(UserService userService, TextField userName) {
        tableView.getItems().clear();
        tableView.getItems().addAll(userService.getUsersByUserName(userName.getText()));
    }

    private void createTableView()
    {
        tableView.getColumns().stream().map(patientTableColumn -> (TableColumn<User, String>) patientTableColumn).
                forEach(patientTableColumn -> patientTableColumn.setCellValueFactory(new PropertyValueFactory<User, String>(patientTableColumn.getId())));
    }

    private void close(Dialog<String> dialog) {
        dialog.setResult("True");
        dialog.close();
    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/search/UserSearch.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            borderPane = (BorderPane) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
