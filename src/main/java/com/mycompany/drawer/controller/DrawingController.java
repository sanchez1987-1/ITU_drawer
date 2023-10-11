package com.mycompany.drawer.controller;

import com.mycompany.drawer.view.DrawingView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Код обработки событий и управления приложением
 */
public class DrawingController extends Application {
    private DrawingView view;

    public void startApplication(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        view = new DrawingView(primaryStage);
        // Add event handlers and control logic here
    }
}
