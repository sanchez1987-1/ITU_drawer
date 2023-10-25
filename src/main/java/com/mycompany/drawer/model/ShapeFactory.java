package com.mycompany.drawer.model;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

import static com.mycompany.drawer.controller.UtilController.*;

public class ShapeFactory {
    public static void createShape(Pane drawingPane, ShapeData shapeData, boolean isFillEnabled) {

        Color color;

        if (isFillEnabled) {
            color = stringToColor(shapeData.getColor()); // Включаем заливку
        } else {
            color = Color.TRANSPARENT; // Выключаем заливку
        }

        Node shapeNode = null; // Инициализируем узел

        switch (shapeData.getShape()) {
            case CIRCLE:
                Circle circle = new Circle();
                circle.setCenterX((shapeData.getStartX() + shapeData.getEndX()) / 2);
                circle.setCenterY((shapeData.getStartY() + shapeData.getEndY()) / 2);
                circle.setRadius(Math.abs(shapeData.getEndX() - shapeData.getStartX()) / 2);
                circle.setStroke(stringToColor(shapeData.getColor()));
                circle.setFill(color);
                shapeNode = circle; // Устанавливаем узел как круг
                break;
            case LINE:
                Line line = new Line();
                line.setStartX(shapeData.getStartX());
                line.setStartY(shapeData.getStartY());
                line.setEndX(shapeData.getEndX());
                line.setEndY(shapeData.getEndY());
                line.setStroke(stringToColor(shapeData.getColor()));
                shapeNode = line; // Устанавливаем узел как линию
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
                shapeNode = square; // Устанавливаем узел как квадрат
                break;
            case RECTANGLE:
                Rectangle rectangle = new Rectangle();
                rectangle.setX(Math.min(shapeData.getStartX(), shapeData.getEndX()));
                rectangle.setY(Math.min(shapeData.getStartY(), shapeData.getEndY()));
                rectangle.setWidth(Math.abs(shapeData.getEndX() - shapeData.getStartX()));
                rectangle.setHeight(Math.abs(shapeData.getEndY() - shapeData.getStartY()));
                rectangle.setStroke(stringToColor(shapeData.getColor()));
                rectangle.setFill(color);
                shapeNode = rectangle; // Устанавливаем узел как прямоугольник
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
                shapeNode = star; // Устанавливаем узел как звезду
                break;
        }

        shapeData.setShapeNode(shapeNode); // Устанавливаем узел для фигуры
        drawingPane.getChildren().add(shapeNode); // Добавляем узел в панель
    }
}
