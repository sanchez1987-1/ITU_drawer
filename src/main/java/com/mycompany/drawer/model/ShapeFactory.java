package com.mycompany.drawer.model;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

import static com.mycompany.drawer.controller.ColorConverter.*;

public class ShapeFactory {
    public static void createShape(Pane drawingPane, ShapeData shapeData, boolean isFillEnabled) {

        Color color;

        if (isFillEnabled) {
            color = stringToColor(shapeData.getColor()); // Включаем заливку
        } else {
            color = Color.TRANSPARENT; // Выключаем заливку
        }

        switch (shapeData.getShape()) {
            case CIRCLE:
                Circle circle = new Circle();
                circle.setCenterX((shapeData.getStartX() + shapeData.getEndX()) / 2);
                circle.setCenterY((shapeData.getStartY() + shapeData.getEndY()) / 2);
                circle.setRadius(Math.abs(shapeData.getEndX() - shapeData.getStartX()) / 2);
                circle.setStroke(stringToColor(shapeData.getColor()));
                circle.setFill(color);
                drawingPane.getChildren().add(circle);
                break;
            case LINE:
                Line line = new Line();
                line.setStartX(shapeData.getStartX());
                line.setStartY(shapeData.getStartY());
                line.setEndX(shapeData.getEndX());
                line.setEndY(shapeData.getEndY());
                line.setStroke(stringToColor(shapeData.getColor()));
                drawingPane.getChildren().add(line);
                break;
            case SQUARE:
                Rectangle square = new Rectangle();
                double size = Math.min(Math.abs(shapeData.getEndX() - shapeData.getStartX()), Math.abs(shapeData.getEndY() - shapeData.getStartY()));
                square.setX(Math.min(shapeData.getStartX(), shapeData.getEndX()));
                square.setY(Math.min(shapeData.getStartY(), shapeData.getEndY()));
                square.setWidth(size);
                square.setHeight(size);
                square.setStroke(stringToColor(shapeData.getColor()));
                square.setFill(color);
                drawingPane.getChildren().add(square);
                break;
            case RECTANGLE:
                Rectangle rectangle = new Rectangle();
                rectangle.setX(Math.min(shapeData.getStartX(), shapeData.getEndX()));
                rectangle.setY(Math.min(shapeData.getStartY(), shapeData.getEndY()));
                rectangle.setWidth(Math.abs(shapeData.getEndX() - shapeData.getStartX()));
                rectangle.setHeight(Math.abs(shapeData.getEndY() - shapeData.getStartY()));
                rectangle.setStroke(stringToColor(shapeData.getColor()));
                rectangle.setFill(color);
                drawingPane.getChildren().add(rectangle);
                break;
            case STAR:
                Polygon star = new Polygon();
                double centerX = (shapeData.getEndX() + shapeData.getStartX()) / 2;
                double centerY = (shapeData.getEndY() + shapeData.getStartY()) / 2;
                double radius = Math.min(Math.abs(shapeData.getEndX() - shapeData.getStartX()), Math.abs(shapeData.getEndY() - shapeData.getStartY())) / 2;

                int numberCorners = 5; // Здесь вы можете использовать количество вершин, заданное в DrawingView
                for (int i = 0; i < 2 * numberCorners; i++) {
                    double angle = Math.toRadians(i * (360.0 / (2 * numberCorners)));
                    double r = (i % 2 == 0) ? radius : radius / 2.0; // Чередование больших и малых радиусов
                    star.getPoints().addAll(centerX + r * Math.cos(angle), centerY + r * Math.sin(angle));
                }

                star.setStroke(stringToColor(shapeData.getColor()));
                star.setFill(color);
                drawingPane.getChildren().add(star);
                break;
        }
    }
}
