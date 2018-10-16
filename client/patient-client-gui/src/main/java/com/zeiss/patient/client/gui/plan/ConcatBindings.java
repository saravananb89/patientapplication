package com.zeiss.patient.client.gui.plan;

import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConcatBindings {
    @SafeVarargs
    public static <T> ListBinding<T> concat(ObservableList<T>... sources) {
        return new ListBinding<T>() {

            {
                bind(sources);
            }

            @Override
            protected ObservableList<T> computeValue() {
                return FXCollections.concat(sources);
            }
        };
    }
}
