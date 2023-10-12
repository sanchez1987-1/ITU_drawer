package com.mycompany.drawer.model;

import javafx.scene.canvas.GraphicsContext;

public class ShapeFactory {

    public static void createShape(GraphicsContext gc, ShapeData shapeData) {
        gc.setStroke(shapeData.getColor());
        switch (shapeData.getShape()) {
            case CIRCLE:
                gc.strokeOval(shapeData.getStartX(), shapeData.getStartY(), shapeData.getEndX() - shapeData.getStartX(), shapeData.getEndY() - shapeData.getStartY());
                break;
            case LINE:
                gc.strokeLine(shapeData.getStartX(), shapeData.getStartY(), shapeData.getEndX(), shapeData.getEndY());
                break;
            case SQUARE:
                gc.strokeRect(shapeData.getStartX(), shapeData.getStartY(), shapeData.getEndX() - shapeData.getStartX(), shapeData.getEndX() - shapeData.getStartX());
                break;
            case RECTANGLE:
                gc.strokeRect(shapeData.getStartX(), shapeData.getStartY(), shapeData.getEndX() - shapeData.getStartX(), shapeData.getEndY() - shapeData.getStartY());
                break;
            case STAR:
                double centerX = (shapeData.getEndX() + shapeData.getStartX()) / 2;
                double centerY = (shapeData.getEndY() + shapeData.getStartY()) / 2;
                double radius = Math.min(Math.abs(shapeData.getEndX() - shapeData.getStartX()), Math.abs(shapeData.getEndY() - shapeData.getStartY())) / 2;

                double[] xPoints = new double[5];
                double[] yPoints = new double[5];

                for (int i = 0; i < 5; i++) {
                    double angle = Math.toRadians(i * 144); // Угол между вершинами 72 градуса (360 / 5)
                    xPoints[i] = centerX + radius * Math.cos(angle);
                    yPoints[i] = centerY + radius * Math.sin(angle);
                }

                gc.strokePolygon(xPoints, yPoints, 5);
                break;
        }
    }
}