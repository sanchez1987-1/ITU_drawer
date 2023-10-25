package com.mycompany.drawer.view;

import com.mycompany.drawer.controller.DrawingController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Класс для создания окна приложения и отображения графики
 */

public class DrawingView extends DrawingController {

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
}