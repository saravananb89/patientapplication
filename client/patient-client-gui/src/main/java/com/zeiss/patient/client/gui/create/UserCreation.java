package com.zeiss.patient.client.gui.create;

import com.google.inject.Inject;
import com.zeiss.patient.client.gui.localeservice.LocaleService;
import com.zeiss.user.service.api.User;
import com.zeiss.user.service.api.UserService;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UserCreation extends TextFieldValidation {

    @FXML
    private Button cancel;
    @FXML
    private Button save;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox<Locale> preferredLocale;

    private VBox vbox;

    @Inject
    private UserService userService;
    @Inject
    private LocaleService localeService;

    public UserCreation() {
    }

    @FXML
    public void initialize() {
        //do nothing
    }

    public void showUserDialog(boolean update, UserService userService, User user, Runnable runnable, Stage parentStage
    ) {
        loadFxml();
        Dialog<String> dialog = new Dialog();

        dialog.initOwner(parentStage);

        dialog.setTitle("User Create or Update");

        userName.textProperty().set(user.getUserName());
        password.textProperty().set(user.getPassword());
        //lastLogin.valueProperty().set(user.getLastLogin());
        // preferredLocale.valueProperty().set(user.getPreferredLocale());
        preferredLocale.setItems(FXCollections.observableArrayList(Locale.GERMAN, Locale.ENGLISH));

        if (update) {
            userName.setDisable(true);
        } else {
            user.userNameProperty().bind(userName.textProperty());
        }
        user.passwordProperty().bind(password.textProperty());
      //  user.lastLoginProperty().bind(lastLogin.valueProperty());
        user.preferredLocaleProperty().bind(preferredLocale.valueProperty());

        BooleanBinding validUserNameBinding = userName.textProperty().isEmpty();
        BooleanBinding validPasswordBinding = password.textProperty().isEmpty();
        BooleanBinding validPreferredLocaleBinding = preferredLocale.valueProperty().isNull();
        //BooleanBinding validLastloginBinding = lastLogin.valueProperty().isNull();

        configureTextFieldBinding(validUserNameBinding, userName, "User Name is required");
        configureTextFieldBinding(validPasswordBinding, password, "Password is required");
       // configureTextFieldBinding(validLastloginBinding, lastLogin, "Lastlogin is required");
        configureTextFieldBinding(validPreferredLocaleBinding, preferredLocale, "Preferred Locale is required");

        save.disableProperty().bind(validUserNameBinding.or(validPasswordBinding).
                or(validPreferredLocaleBinding));

        save.setOnAction(event -> saveAction(userService, user, dialog, runnable));
        cancel.setOnAction(event -> close(dialog, runnable));

        dialog.setHeight(Control.USE_COMPUTED_SIZE);

        dialog.getDialogPane().contentProperty().setValue(vbox);

        dialog.showAndWait();

    }

    private void loadFxml() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/zeiss/patient/client/gui/create/UserCreate.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setResources(ResourceBundle.getBundle("messages", localeService.getLocale()));
            vbox = (VBox) fxmlLoader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveAction(UserService userService, User user, Dialog<String> dialog, Runnable runnable) {
        save(userService, user);
        close(dialog, runnable);
    }

    private void close(Dialog<String> dialog, Runnable runnable) {
        runnable.run();
        dialog.setResult("True");
        dialog.close();
    }

    protected void save(UserService userService, User user) {
        userService.create(user);
    }
}
