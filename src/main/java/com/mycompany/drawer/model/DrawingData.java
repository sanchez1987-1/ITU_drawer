package com.mycompany.drawer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawingData implements Serializable {
    private List<ShapeData> shapes;
    private String bgColor;

    public DrawingData() {
        this.shapes = new ArrayList<>();
        this.bgColor = "";
        // Инициализируйте параметры Pane
    }

    public List<ShapeData> getShapes() {
        return shapes;
    }

    public String getBgColor() { return bgColor; }
    public void setBgColor(String color) { this.bgColor = color;}
}