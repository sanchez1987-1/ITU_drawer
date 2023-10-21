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

                int numberCorners = 5; // Задайте желаемое количество вершин звезды

                double[] xPoints = new double[2 * numberCorners];
                double[] yPoints = new double[2 * numberCorners];

                for (int i = 0; i < 2 * numberCorners; i++) {
                    double angle = Math.toRadians(i * (360.0 / (2 * numberCorners)));
                    double r = (i % 2 == 0) ? radius : radius / 2.0; // Чередование больших и малых радиусов
                    xPoints[i] = centerX + r * Math.cos(angle);
                    yPoints[i] = centerY + r * Math.sin(angle);
                }

                gc.strokePolygon(xPoints, yPoints, 2 * numberCorners);
                break;
        }
    }
}