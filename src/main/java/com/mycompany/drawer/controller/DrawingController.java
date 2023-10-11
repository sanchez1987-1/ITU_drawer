package com.mycompany.drawer.controller;

import com.mycompany.drawer.App;
import com.mycompany.drawer.view.DrawingView;
import javafx.stage.Stage;

/**
 * Код обработки событий и управления приложением
 */
public class DrawingController extends App {

    public DrawingController(Stage primaryStage) {
        new DrawingView(primaryStage);
    }
}
