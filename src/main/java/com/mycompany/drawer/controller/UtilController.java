package com.mycompany.drawer.controller;

import com.mycompany.drawer.model.ShapeData;
import com.mycompany.drawer.model.ShapeEnum;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import static com.mycompany.drawer.model.ShapeFactory.createShape;

public class UtilController {

    // Преобразование Color в строку в формате #RRGGBBAA
    public static String colorToString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        int a = (int) (color.getOpacity() * 255);
        return String.format("#%02X%02X%02X%02X", r, g, b, a);
    }

    // Преобразование строки в объект Color
    public static Color stringToColor(String colorString) {
        if (colorString == null || colorString.isEmpty()) {
            return Color.BLACK; // Возвращаем черный цвет по умолчанию, если строка пуста
        }

        if (colorString.startsWith("#")) {
            colorString = colorString.substring(1); // Удаляем символ # (если есть)
        }

        if (colorString.length() != 8) {
            throw new IllegalArgumentException("Неверный формат цветовой строки. Используйте #RRGGBBAA.");
        }

        int r = Integer.parseInt(colorString.substring(0, 2), 16);
        int g = Integer.parseInt(colorString.substring(2, 4), 16);
        int b = Integer.parseInt(colorString.substring(4, 6), 16);
        int a = Integer.parseInt(colorString.substring(6, 8), 16);

        return Color.rgb(r, g, b, a / 255.0);
    }

    // Преобразуем один вид фигуры в другой
    public static void convertShape(Pane drawingPane, ShapeData shapeData, ShapeEnum newShape) {
        // Удалите текущую фигуру из Pane
        drawingPane.getChildren().removeIf(node -> node.equals(shapeData.getShapeNode()));

        // Измените тип фигуры на новый
        shapeData.setShape(newShape);

        // Создайте новую фигуру с новым типом
        createShape(drawingPane, shapeData, shapeData.getIsFillEnabled());
    }
}