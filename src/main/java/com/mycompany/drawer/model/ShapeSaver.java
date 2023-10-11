package com.mycompany.drawer.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ShapeSaver {
    // Метод для сохранения списка фигур в файл
    public static void saveShapesToFile(String fileName, List<ShapeData> shapes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(shapes);
            System.out.println("Фигуры сохранены в файл: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для загрузки списка фигур из файла
    public static List<ShapeData> loadShapesFromFile(String fileName) {
        List<ShapeData> loadedShapes = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedShapes = (List<ShapeData>) ois.readObject();
            System.out.println("Фигуры загружены из файла: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedShapes;
    }
}
