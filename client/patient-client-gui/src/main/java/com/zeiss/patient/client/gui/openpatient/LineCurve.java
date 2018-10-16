package com.zeiss.patient.client.gui.openpatient;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class LineCurve extends Application {

    private List<Anchor> points = new ArrayList<>();

    @Override
    public void start(Stage stage) {

        Image image = new Image(this.getClass().getResourceAsStream("/sample/Retina.jpg"));

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        StackPane stackPane = new StackPane();
        AnchorPane anchorPane = new AnchorPane();

        Group group = new Group();
        stackPane.getChildren().addAll(imageView, anchorPane);
        anchorPane.getChildren().add(group);
        Scene scene = new Scene(stackPane, 500, 500, Color.BISQUE);
        anchorPane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                double x = event.getX(), y = event.getY();
                if (!points.isEmpty()) {
                    Anchor start = points.get(points.size() - 1);
                    Line line = createCurve(start, x, y, group);
                    Anchor end = new Anchor(Color.TOMATO, line.endXProperty(), line.endYProperty(), 5);
                    group.getChildren().add(end);
                    points.add(end);
                } else {
                    Anchor anchor = new Anchor(Color.TOMATO, x, y, 5);
                    anchor.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            Anchor start = points.get(points.size() - 1);
                            Line line = createCurve(start, anchor.getCenterX(), anchor.getCenterY(), group);
                            line.endXProperty().bind(anchor.centerXProperty());
                            line.endYProperty().bind(anchor.centerYProperty());
                            points.clear();
                            e.consume();
                        }
                    });
                    group.getChildren().add(anchor);
                    points.add(anchor);
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    private Line createCurve(Anchor from, double x2, double y2, Group group) {
        double x1 = from.getCenterX(), y1 = from.getCenterY();
        double distance = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        Line line = new Line();
        line.setStartX(x1);
        line.setStartY(y1);
        line.setEndX(x2);
        line.setEndY(y2);
        line.setStroke(Color.BLUEVIOLET);
        line.setStrokeWidth(4);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setFill(Color.TRANSPARENT);
        line.startXProperty().bind(from.centerXProperty());
        line.startYProperty().bind(from.centerYProperty());
        Line controlLine = new ControlLine(line.startXProperty(), line.startYProperty(), line.endXProperty(), line.endYProperty());
        Anchor control1 = new Anchor(Color.FORESTGREEN, line.startXProperty(), line.startYProperty(), 3);
        Anchor control2 = new Anchor(Color.FORESTGREEN, line.endXProperty(), line.endYProperty(), 3);
        group.getChildren().addAll(line, control1, control2, controlLine);
        return line;
    }

    class ControlLine extends Line {
        ControlLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
            startXProperty().bind(startX);
            startYProperty().bind(startY);
            endXProperty().bind(endX);
            endYProperty().bind(endY);
            setStrokeWidth(2);
            setStroke(Color.FORESTGREEN.deriveColor(0, 1, 1, 0.5));
        }
    }

    // a draggable anchor displayed around a point.
    class Anchor extends Circle {
        Anchor(Color color, DoubleProperty x, DoubleProperty y, double radius) {
            super(x.get(), y.get(), radius);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            x.bind(centerXProperty());
            y.bind(centerYProperty());
            enableDrag();
        }

        Anchor(Color color, double x, double y, double radius) {
            super(x, y, radius);
            setFill(color.deriveColor(1, 1, 1, 0.5));
            setStroke(color);
            setStrokeWidth(2);
            setStrokeType(StrokeType.OUTSIDE);
            enableDrag();
        }

        // make a node movable by dragging it around with the mouse.
        private void enableDrag() {
            final Delta dragDelta = new Delta();
            setOnMousePressed(mouseEvent -> {
                // record a delta distance for the drag and drop operation.
                dragDelta.x = getCenterX() - mouseEvent.getX();
                dragDelta.y = getCenterY() - mouseEvent.getY();
                getScene().setCursor(Cursor.MOVE);
            });
            setOnMouseReleased(mouseEvent -> getScene().setCursor(Cursor.HAND));
            setOnMouseDragged(mouseEvent -> {
                double newX = mouseEvent.getX() + dragDelta.x;
                if (newX > 0 && newX < getScene().getWidth()) {
                    setCenterX(newX);
                }
                double newY = mouseEvent.getY() + dragDelta.y;
                if (newY > 0 && newY < getScene().getHeight()) {
                    setCenterY(newY);
                }
            });
            setOnMouseEntered(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.HAND);
                }
            });
            setOnMouseExited(mouseEvent -> {
                if (!mouseEvent.isPrimaryButtonDown()) {
                    getScene().setCursor(Cursor.DEFAULT);
                }
            });
        }

        // records relative x and y co-ordinates.
        private class Delta {
            double x, y;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}