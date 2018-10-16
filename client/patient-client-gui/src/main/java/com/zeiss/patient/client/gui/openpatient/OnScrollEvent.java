package com.zeiss.patient.client.gui.openpatient;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;

public class OnScrollEvent {

    private static double zoomIntensity = 0.02;
    private static double scaleValue = 0.7;

    public static void onScroll(ScrollPane scrollPane, double wheelDelta, Point2D mousePoint, Node zoomNode, ImageView imageView) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = scrollPane.getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = scrollPane.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = scrollPane.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;

        imageView.setScaleX(scaleValue);
        imageView.setScaleY(scaleValue);

        scrollPane.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = imageView.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = imageView.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        scrollPane.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        scrollPane.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }
}
