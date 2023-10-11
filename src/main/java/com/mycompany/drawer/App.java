package com.mycompany.drawer;

import com.mycompany.drawer.controller.DrawingController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new DrawingController(stage);
    }
}