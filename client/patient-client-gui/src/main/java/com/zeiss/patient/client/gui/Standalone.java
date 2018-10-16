package com.zeiss.patient.client.gui;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class Standalone {
    public static void main(String[] args) {

        Button button = new Button("Continue");
        CheckBox box = new CheckBox("Agree");
        box.setOnAction((e) -> {
            if (box.isSelected()) {
                button.setDisable(false);
            } else {
                button.setDisable(true);
            }
        });

        button.disableProperty().bind(box.selectedProperty());


    }

    public void submit(TextField textField) {
        textField.getStyleClass().remove("invalid");
        if (textField.getText().isEmpty()) {
            textField.getStyleClass().add("invalid");
        }
    }

    static final double BORDER_RADIUS = 4;

    static Border createBorder() {
        return new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(BORDER_RADIUS), BorderStroke.THICK));
    }


}
