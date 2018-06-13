package com.zeiss.gui.create;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

public class TextFieldValidation {

    public static void configureTextFieldBinding(BooleanBinding binding, Control textField, String message) {
        if (textField.getTooltip() == null) {
            textField.setTooltip(new Tooltip());
        }
        String tooltipText = textField.getTooltip().getText();
        binding.addListener((obs, oldValue, newValue) -> {
            updateTextFieldValidationStatus(textField, tooltipText, newValue, message);
        });
        updateTextFieldValidationStatus(textField, tooltipText, binding.get(), message);
    }

    public static void configureTextFieldBinding(BooleanBinding binding1, BooleanBinding binding2, Control textField, String message1, String message2) {
        if (textField.getTooltip() == null) {
            textField.setTooltip(new Tooltip());
        }
        binding1.addListener((obs, oldValue, newValue) -> {
            updateTextFieldValidationStatus(textField, newValue, binding2.get(), message1, message2);
        });
        binding2.addListener((obs, oldValue, newValue) -> {
            updateTextFieldValidationStatus(textField, binding1.get(), newValue, message1, message2);
        });
        updateTextFieldValidationStatus(textField, binding1.get(), binding2.get(), message1, message2);
    }

    public static void updateTextFieldValidationStatus(Control control, boolean invalid1, boolean invalid2, String message1, String message2) {
        control.pseudoClassStateChanged(PseudoClass.getPseudoClass("validation-error"), invalid1 || invalid2);

        Platform.runLater(() -> control.getChildrenUnmodifiable().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("validation-error"),
                invalid1 || invalid2)));
        String tooltipText;
        if (invalid1) {
            tooltipText = message1;
        } else if (invalid2) {
            tooltipText = message2;
        } else {
            tooltipText = "";
        }
        if (tooltipText == null || tooltipText.isEmpty()) {
            control.setTooltip(null);
        } else {
            Tooltip tooltip = control.getTooltip();
            if (tooltip == null) {
                control.setTooltip(new Tooltip(tooltipText));
            } else {
                tooltip.setText(tooltipText);
            }
        }
    }

    public static void updateTextFieldValidationStatus(Control control,
                                                       String defaultTooltipText, boolean invalid, String message) {
        control.pseudoClassStateChanged(PseudoClass.getPseudoClass("validation-error"), invalid);

        Platform.runLater(() -> control.getChildrenUnmodifiable().forEach(node -> node.pseudoClassStateChanged(PseudoClass.getPseudoClass("validation-error"),
                invalid)));
        String tooltipText;
        if (invalid) {
            tooltipText = message;
        } else {
            tooltipText = defaultTooltipText;
        }
        if (tooltipText == null || tooltipText.isEmpty()) {
            control.setTooltip(null);
        } else {
            Tooltip tooltip = control.getTooltip();
            if (tooltip == null) {
                control.setTooltip(new Tooltip(tooltipText));
            } else {
                tooltip.setText(tooltipText);
            }
        }
    }
}
