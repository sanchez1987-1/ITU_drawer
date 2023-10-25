package com.mycompany.drawer.controller;

import com.mycompany.drawer.App;
import com.mycompany.drawer.model.ShapeData;
import com.mycompany.drawer.model.ShapeEnum;
import com.mycompany.drawer.model.ShapeFactory;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static com.mycompany.drawer.controller.ColorConverter.colorToString;
import static com.mycompany.drawer.model.ShapeEnum.CIRCLE;

/**
 * Код обработки событий и управления приложением
 */
public class DrawingController extends App {
    protected ShapeEnum currentShape = CIRCLE;
    protected boolean isConvertMode = false; // Флаг для отслеживания режима конвертирования
    protected List<ShapeData> shapes = new ArrayList<>();
    protected Pane drawingPane;
    private FileController fc = new FileController();
    private String currentColor = colorToString(Color.BLACK);
    private double startX, startY, endX, endY, offsetX, offsetY;
    private boolean isFillEnabled = false; // Флаг для отслеживания состояния заливки
    private boolean isMoveEnabled = false; // Флаг для отслеживания перемещения

    protected Button createToolButton(String text, ShapeEnum shapeType) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);

        button.setOnAction(e -> {
            if(isConvertMode) {
                ShapeData shape = shapes.get(shapes.size() - 1);
                ShapeFactory.convertShape(drawingPane, shape, shapeType);
            } else {
                currentShape = shapeType;
            }
        });

        return button;
    }

    protected VBox createMenuContainer() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Файл");
        MenuItem saveItem = new MenuItem("Сохранить");
        MenuItem exportItem = new MenuItem("Сохранить как PNG");
        MenuItem openItem = new MenuItem("Открыть");
        fileMenu.getItems().addAll(saveItem, exportItem, openItem);
        menuBar.getMenus().add(fileMenu);

        VBox menuContainer = new VBox();
        menuContainer.getChildren().addAll(menuBar);

        saveItem.setOnAction(e -> fc.saveShapesToFile(shapes));
        openItem.setOnAction(e -> {
            fc.loadShapesFromFile(shapes);
            redrawShapes();
        });
        exportItem.setOnAction(e -> fc.saveAsImage(drawingPane));

        return menuContainer;
    }

    protected VBox createToolsContainer() {
        VBox toolsContainer = new VBox();
        toolsContainer.setSpacing(5);
        toolsContainer.setStyle("-fx-background-color: white;"); // Установка цвета фона

        // Обработчик события при нажатии кнопки мыши
        drawingPane.setOnMousePressed(e -> {
            startX = e.getX();
            startY = e.getY();
        });

        // Обработчик события при отпускании кнопки мыши
        drawingPane.setOnMouseReleased(e -> {
            endX = e.getX();
            endY = e.getY();
            if (!isMoveEnabled) {
                shapes.add(new ShapeData(currentShape, startX, startY, endX, endY, currentColor, isFillEnabled));
            } else {
                offsetX = endX-startX;
                offsetY = endY-startY;
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

        Button circleButton = createToolButton("Круг", CIRCLE);
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
        ToggleButton convertButton = new ToggleButton("Конвертировать");
        convertButton.setOnAction(e -> {
            isConvertMode = convertButton.isSelected();
        });

        ColorPicker colorPicker = new ColorPicker(); // Обработчик события выбора цвета
        colorPicker.setOnAction(e -> {
            currentColor = colorToString(colorPicker.getValue());
        });

        editButArea.getChildren().addAll(circleButton, lineButton, squareButton, rectangleButton, starButton, colorPicker, fillButton, convertButton);

        toolsContainer.getChildren().addAll(editButArea, moveButton);

        return toolsContainer;
    }

    // Метод для перерисовки всех сохраненных фигур
    private void redrawShapes() {
        drawingPane.getChildren().clear();
        for (ShapeData shape : shapes) {
            ShapeFactory.createShape(drawingPane, shape, shape.getIsFillEnabled());
        }
    }
}
