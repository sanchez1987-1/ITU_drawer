package com.mycompany.drawer;

import com.mycompany.drawer.view.DrawingView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 * Приложение для рисования различных фигур
 */
public class App extends Application {
    protected Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        new DrawingView(stage);
    }
}