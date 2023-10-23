package com.mycompany.drawer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DrawingData implements Serializable {
    private List<ShapeData> shapes;

    public DrawingData() {
        this.shapes = new ArrayList<>();
        // Инициализируйте параметры Pane
    }

    public List<ShapeData> getShapes() {
        return shapes;
    }
}