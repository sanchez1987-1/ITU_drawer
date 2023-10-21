package com.mycompany.drawer.view;

import com.mycompany.drawer.App;
import com.mycompany.drawer.model.ShapeFactory;
import com.mycompany.drawer.model.ShapeData;
import com.mycompany.drawer.model.ShapeEnum;
import com.mycompany.drawer.controller.ShapeSaver;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для создания окна приложения и отображения графики
 */


public class DrawingView extends App {
    private Stage primaryStage;
    private int numberCorners = 5; // По умолчанию 5 вершин

    private ShapeEnum currentShape = ShapeEnum.CIRCLE;
    private Color currentColor = Color.BLACK;
    private double startX, startY;
    private double endX, endY;
    private boolean isDrawing = false;
    protected List<ShapeData> shapes = new ArrayList<>();

    ShapeSaver saver = new ShapeSaver();

    public DrawingView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        TextField cornersField = new TextField("5"); // Значение по умолчанию
        cornersField.setPromptText("Количество вершин");
        cornersField.setLayoutY(650);
        cornersField.setLayoutX(750);

        primaryStage.setTitle("Рисование фигур");
        Group root = new Group();
        Canvas canvas = new Canvas(1200, 1000);
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

        Button circleButton = new Button("Круг");
        circleButton.setOnAction(e -> currentShape = ShapeEnum.CIRCLE);
        Button lineButton = new Button("Линия");
        lineButton.setOnAction(e -> currentShape = ShapeEnum.LINE);
        Button squareButton = new Button("Квадрат");
        squareButton.setOnAction(e -> currentShape = ShapeEnum.SQUARE);
        Button rectangleButton = new Button("Прямоугольник");
        rectangleButton.setOnAction(e -> currentShape = ShapeEnum.RECTANGLE);
        Button starButton = new Button("Звезда");
        starButton.setOnAction(e -> currentShape = ShapeEnum.STAR);

        Button saveButton = new Button("Сохранить");
        Button loadButton = new Button("Загрузить");

//        saveButton.setOnAction(e -> saver.saveShapesToFile());
//        loadButton.setOnAction(e -> saver.loadShapesFromFile());

        ColorPicker colorPicker = new ColorPicker(Color.BLACK);

        // Обработчик события выбора цвета
        colorPicker.setOnAction(e -> {
            currentColor = colorPicker.getValue();
        });

        Button drawButton = new Button("Произвольная звезда");
        drawButton.setLayoutY(650);
        drawButton.setLayoutX(650);

        drawButton.setOnAction(e -> {
            try {
                numberCorners = Integer.parseInt(cornersField.getText());
                redrawShapes(gc);
            } catch (NumberFormatException ex) {
                // Обработка ошибки, если пользователь ввел некорректное значение
                // Можно вывести сообщение об ошибке
            }
        });

        root.getChildren().addAll(canvas, circleButton, lineButton, squareButton, rectangleButton, starButton, colorPicker, saveButton, loadButton, cornersField, drawButton);

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

        saveButton.setLayoutY(610);
        saveButton.setLayoutX(550);
        loadButton.setLayoutY(610);
        loadButton.setLayoutX(650);

        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Метод для перерисовки всех сохраненных фигур
    private void redrawShapes(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (ShapeData shape : shapes) {
            if (shape.getShape() == ShapeEnum.RANDOM_STAR) {
                drawStar(gc, shape.getStartX(), shape.getStartY(), shape.getEndX(), shape.getEndY(), shape.getColor());
            } else {
                ShapeFactory.createShape(gc, shape);
            }
        }
    }

    private void drawStar(GraphicsContext gc, double startX, double startY, double endX, double endY, Color color) {
        double centerX = (endX + startX) / 2;
        double centerY = (endY + startY) / 2;
        double radius = Math.min(Math.abs(endX - startX), Math.abs(endY - startY)) / 2;

        double[] xPoints = new double[2 * numberCorners];
        double[] yPoints = new double[2 * numberCorners];

        for (int i = 0; i < 2 * numberCorners; i++) {
            double angle = Math.toRadians(i * (360.0 / (2 * numberCorners)));
            double r = (i % 2 == 0) ? radius : radius / 2.0; // Чередование больших и малых радиусов
            xPoints[i] = centerX + r * Math.cos(angle);
            yPoints[i] = centerY + r * Math.sin(angle);
        }

        gc.setStroke(color);
        gc.strokePolygon(xPoints, yPoints, 2 * numberCorners);
    }

}
