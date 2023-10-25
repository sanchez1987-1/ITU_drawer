package com.mycompany.drawer.controller;

import com.mycompany.drawer.model.DrawingData;
import com.mycompany.drawer.model.ShapeData;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class FileController {
    public String loadShapesFromFile(List<ShapeData> shapes) {
        // Загрузка данных
        String bgColor = "";
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Shape files *.dat", "*.dat"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Задайте имя файла на основе выбора пользователя
            String fileName = file.getAbsolutePath();
            try {
                DrawingData dataToLoad = loadDrawingData(new File(fileName));
                shapes.clear();
                bgColor = dataToLoad.getBgColor();
                shapes.addAll(dataToLoad.getShapes()); // Обновление списка фигур из загруженных данных
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return bgColor;
    }

    public void saveShapesToFile(List<ShapeData> shapes, String bgColor) {
        // Сохранение данных
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Shape files *.dat", "*.dat"));
        File file = fileChooser.showSaveDialog(null);
        DrawingData dataToSave = new DrawingData();

        if (file != null) {
            // Задайте имя файла на основе выбора пользователя
            String fileName = file.getAbsolutePath();
            try {
                dataToSave.getShapes().addAll(shapes); // shapes - список ваших фигур
                dataToSave.setBgColor(bgColor);
                saveDrawingData(dataToSave, new File(fileName));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void saveAsImage(Pane drawingPane) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.png)", "*.png"));
        fileChooser.setInitialFileName("drawing.png");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            // Создайте объект WritableImage и задайте его размер равным размеру drawingPane
            WritableImage writableImage = new WritableImage((int) drawingPane.getWidth(), (int) drawingPane.getHeight());

            // Создайте объект SnapshotParameters, если хотите сохранить фон и другие параметры сцены
            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT); // Задайте фон, если это необходимо

            // Сделайте снимок содержимого drawingPane и сохраните его в WritableImage
            drawingPane.snapshot(params, writableImage);

            // Задайте имя файла на основе выбора пользователя
            String fileName = file.getAbsolutePath();

            try {
                // Запишите изображение в файл JPEG
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);

                // Запишите изображение в файл PNG
                ImageIO.write(bufferedImage, "png", new File(fileName));
                System.out.println("Файл сохранен: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveDrawingData(DrawingData data, File file) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            outputStream.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DrawingData loadDrawingData(File file) {
        DrawingData data = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            data = (DrawingData) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
}