package com.zeiss.patient.client.gui;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.control.ButtonBar;
import org.controlsfx.dialog.Dialog;

import java.util.Locale;

public class GuiStarter {

    private final Button actionLogin = new Button("Login");
    private final Button cancel = new Button("Cancel");
    private String statusLabel = "";

    @Inject
    private PatientView patientView;

    public void start(Stage primaryStage) {
        loadLoginDialog(primaryStage);
    }

    public void loadLoginDialog(Stage primaryStage) {
        loadStage(primaryStage, null, true);
        Dialog dlg = new Dialog(primaryStage, "Login Dialog");
        TextField username = new TextField();
        PasswordField password = new PasswordField();
        actionLogin.setOnAction(event -> {
            if ((username.getText().equals("admin") && password.getText().equals("admin")) ||
                    (username.getText().equals("user") && password.getText().equals("user"))) {
                statusLabel = "Logged in as " + username.getText();
                dlg.hide();
                primaryStage.close();
                if (username.getText().equals("admin")) {
                    loadStage(primaryStage, Locale.ENGLISH, false);
                } else if (username.getText().equals("user")) {
                    loadStage(primaryStage, Locale.GERMAN, false);
                }
            }
        });

        cancel.setOnAction(event -> {
            // real login code here
            dlg.hide();
            primaryStage.close();
            System.exit(0);
        });

        // Create the custom dialog.

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        username.setPromptText("Username");
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        ButtonBar.setType(actionLogin, ButtonBar.ButtonType.OK_DONE);
        actionLogin.disableProperty().set(true);
        ButtonBar.setType(cancel, ButtonBar.ButtonType.CANCEL_CLOSE);

        // Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            validate(newValue, password.getText());
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            validate(username.getText(), newValue);
        });

        dlg.setMasthead("Look, a Custom Login Dialog");
        BorderPane borderPane = new BorderPane();
        borderPane.centerProperty().set(grid);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(actionLogin, cancel);
        borderPane.bottomProperty().set(hBox);
        dlg.setContent(borderPane);
        dlg.getActions().addAll();

        // Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

        dlg.show();
    }

    private void validate(String userName, String password) {
        actionLogin.disableProperty().set(userName.trim().isEmpty() || password.trim().isEmpty());
    }

    public void loadStage(Stage primaryStage, Locale locale, boolean opaqueVisible) {
        if (locale == null) {
            locale = patientView.getLocaleService().getLocale();
        }
        patientView.buildGui(locale, statusLabel);
        patientView.opaqueDisable(opaqueVisible);
        Parent parent = patientView.patientShow();
        Scene scene = new Scene(parent, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
