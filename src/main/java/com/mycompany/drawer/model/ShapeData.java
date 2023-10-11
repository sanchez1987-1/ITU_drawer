package com.mycompany.drawer.model;

import javafx.scene.paint.Color;

/**
 * Класс для хранения информации о фигуре
 */
public class ShapeData {
    private ShapeEnum shape;
    private double startX, startY, endX, endY;
    private Color color;

    public ShapeEnum getShape() {
        return shape;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public Color getColor() {
        return color;
    }

    public ShapeData(ShapeEnum shape, double startX, double startY, double endX, double endY, Color color) {
        this.shape = shape;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }
}