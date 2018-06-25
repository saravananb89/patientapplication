package com.zeiss.patient.client.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.zeiss.patient.client.gui.GuiStarter;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String args[]) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new InjectionModule());
        GuiStarter guiStarter = injector.getInstance(GuiStarter.class);
        guiStarter.start(primaryStage);
    }
}
