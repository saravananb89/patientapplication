package com.zeiss.patient.client.gui.openpatient;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.StrokeLineCap;

class BoundLine extends Line {
    BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
        startXProperty().bind(startX);
        startYProperty().bind(startY);

        endXProperty().bind(endX);
        endYProperty().bind(endY);
        setStrokeWidth(2);

        MoveTo moveTo=new MoveTo();

        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
        setMouseTransparent(true);
    }
}
