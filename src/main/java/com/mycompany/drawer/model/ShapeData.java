package com.mycompany.drawer.model;

import javafx.scene.Node;

import java.io.Serializable;

/**
 * Класс для хранения информации о фигуре
 */
public class ShapeData implements Serializable {
    private ShapeEnum shape;
    private double startX, startY, endX, endY;
    private String color;
    private boolean isFillEnabled; // Флаг для отслеживания состояния заливки
    private transient Node shapeNode; // Добавляем поле Node

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

    public String getColor() { return color; }
    public boolean getIsFillEnabled() { return isFillEnabled; }

    public void setShape(ShapeEnum shape) {
        this.shape = shape;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public void setEndY(double endY) {
        this.endY = endY;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFillEnabled() {
        return isFillEnabled;
    }

    public void setFillEnabled(boolean fillEnabled) {
        isFillEnabled = fillEnabled;
    }

    public Node getShapeNode() {
        return shapeNode;
    }

    public void setShapeNode(Node shapeNode) {
        this.shapeNode = shapeNode;
    }

    public ShapeData(ShapeEnum shape, double startX, double startY, double endX, double endY, String color, boolean isFillEnabled) {
        this.shape = shape;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.isFillEnabled = isFillEnabled;
        this.shapeNode = null; // Изначально у нас нет узла
    }
}