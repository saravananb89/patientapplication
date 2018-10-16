package com.zeiss.patient.client.gui.openpatient;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LineWithArrow extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Arrow arrow = new Arrow();
        root.getChildren().add(arrow);

        root.setOnMouseClicked(evt -> {
            switch (evt.getButton()) {
                case PRIMARY:
                    // set pos of end with arrow head
                    arrow.setEndX(evt.getX());
                    arrow.setEndY(evt.getY());
                    break;
                case SECONDARY:
                    // set pos of end without arrow head
                    arrow.setStartX(evt.getX());
                    arrow.setStartY(evt.getY());
                    break;
            }
        });

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
