package com.zeiss.patient.client.gui.openpatient;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class JavaFX_DrawOnCanvas extends Application {

    private double from_x = 0;
    private double from_y = 0;
    private double to_x = 0;
    private double to_y = 0;
    private int line_no = 1;

    private Pair<Double, Double> initialTouch;

    private Map<Integer, Pair<Double, Double>> lines;

    @Override
    public void start(Stage primaryStage) {

        Image image = new Image(this.getClass().getResourceAsStream("/sample/Retina.jpg"));

        ImageView imageView = new ImageView();
        imageView.setImage(image);

        StackPane root = new StackPane();

        AnchorPane anchorPane = new AnchorPane();

        Label label = new Label();

        Group group = new Group();
        root.getChildren().addAll(imageView, anchorPane);
        anchorPane.getChildren().add(group);
        Canvas canvas = new Canvas(400, 400);
        final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        this.initDraw(graphicsContext);

        IntegerProperty count = new SimpleIntegerProperty();

        Pane stack = new Pane();

        ObjectBinding<Bounds> label1InStack = Bindings.createObjectBinding(() -> {
            Bounds label1InScene = label.localToScene(label.getBoundsInLocal());
            return stack.sceneToLocal(label1InScene);
        }, label.boundsInLocalProperty(), label.localToSceneTransformProperty(), stack.localToSceneTransformProperty());

        DoubleBinding startX = Bindings.createDoubleBinding(() -> label1InStack.get().getMaxX(), label1InStack);
        DoubleBinding startY = Bindings.createDoubleBinding(() -> label1InStack.get().getMaxY(), label1InStack);

        DoubleBinding endX = Bindings.createDoubleBinding(() -> label1InStack.get().getMinX(), label1InStack);
        DoubleBinding endY = Bindings.createDoubleBinding(() -> {
            Bounds b = label1InStack.get();
            return b.getMinY() + b.getHeight() / 2;
        }, label1InStack);


        anchorPane.setOnMousePressed((event) -> {
            this.setFromPos(event);
            Canvas newLayer = new Canvas(400, 400);
            GraphicsContext context = newLayer.getGraphicsContext2D();
            initDraw(context);
            group.getChildren().add(0, newLayer);
            initialTouch = new Pair<>(event.getSceneX(), event.getSceneY());
            lines = new HashMap<>();
            lines.put(count.getValue(), new Pair<>(event.getSceneX(), event.getSceneY()));
        });
        anchorPane.setOnMouseDragged((event) -> {
            System.out.println("mouse drag" + event.getSceneX() + event.getSceneY());
            group.getChildren().remove(0);
            final Canvas temp_canvas = new Canvas(400, 400);
            final GraphicsContext gc = temp_canvas.getGraphicsContext2D();
            this.setToPos(event);
            this.drawLine(gc);
            group.getChildren().add(0, temp_canvas);
            lines.put(count.intValue() + 1, new Pair<>(event.getSceneX(), event.getSceneY()));
        });
        anchorPane.setOnMouseReleased((event) -> {
            final Canvas new_line = new Canvas(400, 400);
            final GraphicsContext gc = new_line.getGraphicsContext2D();
            this.setToPos(event);
            this.drawLine(gc);
            drawArrow(gc, (int) from_x, (int) from_y, (int) to_x, (int) to_y);

            lines.put(count.intValue() + 1, new Pair<>(event.getSceneX(), event.getSceneY()));

            //final new stright line
            group.getChildren().add(line_no++, new_line);
        });

        Scene scene = new Scene(root, 400, 400);

        primaryStage.setTitle("java-buddy.blogspot.com");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private final int ARR_SIZE = 8;

    Font fontLarge = Font.font("Droid Sans", FontWeight.BOLD, 15);

    void drawArrow(GraphicsContext gc, int x1, int y1, int x2, int y2) {
        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0},
                4);

        gc.setFont(fontLarge);
        double distance = Math.sqrt((to_x - from_x) * (to_x - from_x) + (to_y - from_y) * (to_y - from_y));

        gc.setFill(Color.web("#010a23"));
        gc.fillRect(from_x, from_y, 18, 18);
        gc.setFill(Color.web("#4bf221"));
        gc.fillText(String.valueOf(distance), from_x + 13, from_y+13);


    }

    private void drawLine(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.strokeLine(initialTouch.getKey(), initialTouch.getValue(), to_x, to_y);

    }

    private void initDraw(GraphicsContext gc) {
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

    }

    private void setFromPos(MouseEvent event) {
        this.from_x = event.getSceneX();
        this.from_y = event.getSceneY();
    }

    private void setToPos(MouseEvent event) {
        this.to_x = event.getSceneX();
        this.to_y = event.getSceneY();
    }
}
