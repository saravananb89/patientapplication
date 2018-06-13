package com.zeiss.gui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zeiss.gui.module.InjectionModule;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;


public class Main extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        loadStage(primaryStage, null);

    }

    public static void loadStage(Stage primaryStage, Locale locale) {
        Injector injector = Guice.createInjector(new InjectionModule());
        PatientView patientView = injector.getInstance(PatientView.class);
        if (locale == null) {
            locale = patientView.getLocaleService().getLocale();
        }
        patientView.buildGui(locale);
        Parent parent = patientView.patientShow();
        Scene scene = new Scene(parent, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
