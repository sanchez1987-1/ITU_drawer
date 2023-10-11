package com.mycompany.drawer.model;

import javafx.scene.paint.Color;
/**
 * Класс для хранения информации о фигуре
 */
public class ShapeData {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
    private Color color;

    public ShapeData(double startX, double startY, double endX, double endY, Color color) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
    }
}
