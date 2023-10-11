package com.mycompany.drawer.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
/**
 * Класс для создания окна приложения и отображения графики
 */
public class DrawingView {
    private Stage primaryStage;

    public DrawingView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Рисование фигур");
        Group root = new Group();
        // Создайте холст и добавьте элементы интерфейса здесь
        Canvas canvas = new Canvas(800, 600);
        // Создайте кнопки, цветовой пикер и другие элементы интерфейса

        root.getChildren().add(canvas);
        // Добавьте остальные элементы интерфейса

        Scene scene = new Scene(root, 800, 650);
        primaryStage.setScene(scene);
    }
}
