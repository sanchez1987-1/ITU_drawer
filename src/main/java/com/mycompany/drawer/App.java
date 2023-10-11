package com.mycompany.drawer;

import com.mycompany.drawer.controller.DrawingController;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private enum Shape { CIRCLE, LINE, SQUARE, RECTANGLE, STAR }

    private Shape currentShape = Shape.CIRCLE;
    private Color currentColor = Color.BLACK;
    private double startX, startY;
    private double endX, endY;
    private List<ShapeData> shapes = new ArrayList<>();
    private boolean isDrawing = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Рисование фигур");
        Group root = new Group();
        Canvas canvas = new Canvas(800, 600);
        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // Обработчик события при нажатии кнопки мыши
        canvas.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            isDrawing = true;
        });

        // Обработчик события при отпускании кнопки мыши
        canvas.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            isDrawing = false;
            shapes.add(new ShapeData(currentShape, startX, startY, endX, endY, currentColor));
            redrawShapes(gc);
        });

        // Обработчик события при перемещении мыши (для рисования)
        Button circleButton = new Button("Круг");
        circleButton.setOnAction(e -> currentShape = Shape.CIRCLE);
        Button lineButton = new Button("Линия");
        lineButton.setOnAction(e -> currentShape = Shape.LINE);
        Button squareButton = new Button("Квадрат");
        squareButton.setOnAction(e -> currentShape = Shape.SQUARE);
        Button rectangleButton = new Button("Прямоугольник");
        rectangleButton.setOnAction(e -> currentShape = Shape.RECTANGLE);
        Button starButton = new Button("Звездочка");
        starButton.setOnAction(e -> currentShape = Shape.STAR);

        ColorPicker colorPicker = new ColorPicker();

        // Обработчик события выбора цвета
        colorPicker.setOnAction(e -> {
            currentColor = colorPicker.getValue();
        });

        root.getChildren().addAll(canvas, circleButton, lineButton, squareButton, rectangleButton, starButton, colorPicker);

        circleButton.setLayoutY(610);
        circleButton.setLayoutX(10);
        lineButton.setLayoutY(610);
        lineButton.setLayoutX(80);
        squareButton.setLayoutY(610);
        squareButton.setLayoutX(160);
        rectangleButton.setLayoutY(610);
        rectangleButton.setLayoutX(250);
        starButton.setLayoutY(610);
        starButton.setLayoutX(360);
        colorPicker.setLayoutY(610);
        colorPicker.setLayoutX(460);

        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для перерисовки всех сохраненных фигур
    private void redrawShapes(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (ShapeData shape : shapes) {
            gc.setStroke(shape.color);
            switch (shape.shape) {
                case CIRCLE:
                    gc.strokeOval(shape.startX, shape.startY, shape.endX - shape.startX, shape.endY - shape.startY);
                    break;
                case LINE:
                    gc.strokeLine(shape.startX, shape.startY, shape.endX, shape.endY);
                    break;
                case SQUARE:
                    gc.strokeRect(shape.startX, shape.startY, shape.endX - shape.startX, shape.endX - shape.startX);
                    break;
                case RECTANGLE:
                    gc.strokeRect(shape.startX, shape.startY, shape.endX - shape.startX, shape.endY - shape.startY);
                    break;
                case STAR:
                    double[] xPoints = {shape.endX, shape.endX + 20, shape.endX + 30, shape.endX + 40, shape.endX + 50};
                    double[] yPoints = {shape.endY + 50, shape.endY, shape.endY + 50, shape.endY, shape.endY + 50};
                    gc.strokePolygon(xPoints, yPoints, 5);
                    break;
            }
        }
    }

    // Класс для хранения информации о фигуре
    private class ShapeData {
        private Shape shape;
        private double startX, startY, endX, endY;
        private Color color;

        ShapeData(Shape shape, double startX, double startY, double endX, double endY, Color color) {
            this.shape = shape;
            this.startX = startX;
            this.startY = startY;
            this.endX = endX;
            this.endY = endY;
            this.color = color;
        }
    }
}