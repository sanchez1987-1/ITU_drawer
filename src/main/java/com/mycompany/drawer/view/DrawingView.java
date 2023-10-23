package com.mycompany.drawer.view;

import com.mycompany.drawer.App;
import com.mycompany.drawer.model.DrawingData;
import com.mycompany.drawer.model.ShapeFactory;
import com.mycompany.drawer.model.ShapeData;
import com.mycompany.drawer.model.ShapeEnum;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import javafx.stage.FileChooser;
import java.io.File;

import static com.mycompany.drawer.controller.ColorConverter.*;

/**
 * Класс для создания окна приложения и отображения графики
 */

public class DrawingView extends App {
    private Stage primaryStage;
    private ShapeEnum currentShape = ShapeEnum.CIRCLE;
    private String currentColor = colorToString(Color.BLACK);
    private double startX, startY, endX, endY, offsetX, offsetY;
    private boolean isDrawing = false;
    private List<ShapeData> shapes = new ArrayList<>();
    private Pane drawingPane;
    private boolean isFillEnabled = false; // Флаг для отслеживания состояния заливки
    private boolean isMoveEnabled = false; // Флаг для отслеживания перемещения

    public DrawingView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Рисование фигур");

        drawingPane = new Pane();

        VBox menuContainer = createMenuContainer();
        VBox toolsContainer = createToolsContainer();

        BorderPane root = new BorderPane();
        root.setCenter(drawingPane);
        root.setTop(menuContainer);
        root.setLeft(toolsContainer);

        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox createToolsContainer() {
        VBox toolsContainer = new VBox();
        toolsContainer.setSpacing(5);
        toolsContainer.setStyle("-fx-background-color: white;"); // Установка цвета фона

        // Обработчик события при нажатии кнопки мыши
        drawingPane.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
            isDrawing = true;
        });

        // Обработчик события при отпускании кнопки мыши
        drawingPane.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            isDrawing = false;
            if (!isMoveEnabled) {
                shapes.add(new ShapeData(currentShape, startX, startY, endX, endY, currentColor, isFillEnabled));
            } else {
                offsetX = endX-startX;
                offsetY = endY-startY;
                isDrawing = false;
                int idx;
                if (shapes.size()>0) {
                    ShapeData shape = shapes.get(shapes.size() - 1);
                    shape.setStartX(shape.getStartX()+offsetX);
                    shape.setStartY(shape.getStartY()+offsetY);
                    shape.setEndX(shape.getEndX()+offsetX);
                    shape.setEndY(shape.getEndY()+offsetY);
                }
            }
            redrawShapes();
        });

        VBox editButArea = new VBox();

        Button circleButton = createToolButton("Круг", ShapeEnum.CIRCLE);
        Button lineButton = createToolButton("Линия", ShapeEnum.LINE);
        Button squareButton = createToolButton("Квадрат", ShapeEnum.SQUARE);
        Button rectangleButton = createToolButton("Прямоугольник", ShapeEnum.RECTANGLE);
        Button starButton = createToolButton("Звездочка", ShapeEnum.STAR);
        ToggleButton fillButton = new ToggleButton("Заливка");
        fillButton.setSelected(false); // По умолчанию без заливки
        fillButton.setOnAction(e -> {
            isFillEnabled = fillButton.isSelected();
        });
        ToggleButton moveButton = new ToggleButton("Перемещать");
        moveButton.setSelected(false); // По умолчанию без заливки
        moveButton.setOnAction(e -> {
            isMoveEnabled = moveButton.isSelected();
            if(!isMoveEnabled) {
                editButArea.setDisable(false);
            } else {
                editButArea.setDisable(true);
            }
        });

        ColorPicker colorPicker = new ColorPicker(); // Обработчик события выбора цвета
        colorPicker.setOnAction(e -> {
            currentColor = colorToString(colorPicker.getValue());
        });

        editButArea.getChildren().addAll(circleButton, lineButton, squareButton, rectangleButton, starButton, colorPicker, fillButton);

        toolsContainer.getChildren().addAll(editButArea, moveButton);

        return toolsContainer;
    }

    private Button createToolButton(String text, ShapeEnum shapeType) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);

        button.setOnAction(e -> {
            currentShape = shapeType;
        });

        return button;
    }

    private VBox createMenuContainer() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        MenuItem saveItem = new MenuItem("Сохранить");
        MenuItem exportItem = new MenuItem("Сохранить как PNG");
        MenuItem openItem = new MenuItem("Открыть");
        fileMenu.getItems().addAll(saveItem, exportItem, openItem);
        menuBar.getMenus().add(fileMenu);

        VBox menuContainer = new VBox();
        menuContainer.getChildren().addAll(menuBar);

        saveItem.setOnAction(e -> saveShapesToFile());
        openItem.setOnAction(e -> loadShapesFromFile());
        exportItem.setOnAction(e -> saveAsImage());

        return menuContainer;
    }

    private void loadShapesFromFile() {
        // Загрузка данных
        DrawingData loadedData = loadDrawingData(new File("C:\\JavaFX\\drawing_data.ser"));
        shapes.clear();
        shapes.addAll(loadedData.getShapes()); // Обновление списка фигур из загруженных данных
        redrawShapes(); // Перерисовать фигуры
    }

    private void saveShapesToFile() {
        // Сохранение данных
        DrawingData dataToSave = new DrawingData();
        dataToSave.getShapes().addAll(shapes); // shapes - список ваших фигур
        saveDrawingData(dataToSave, new File("C:\\JavaFX\\drawing_data.ser"));
    }

    private void saveAsImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPEG files (*.png)", "*.png"));
        fileChooser.setInitialFileName("drawing.png");
//        File file = fileChooser.showSaveDialog(primaryStage);
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

    // Метод для перерисовки всех сохраненных фигур
    private void redrawShapes() {
        drawingPane.getChildren().clear();
        for (ShapeData shape : shapes) {
            ShapeFactory.createShape(drawingPane, shape, shape.getIsFillEnabled());
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