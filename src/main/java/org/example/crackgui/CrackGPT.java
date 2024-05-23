package org.example.crackgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CrackGPT extends Application {

    MenuComponent menu = new MenuComponent();

    public ResourceBundle language;

    public void start(Stage stage) throws IOException {

        menu.menuInitialize();

        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("CrackGPT-4");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }


    public Scene createScene() {

        Scene scene = new Scene(menu.getRoot(),800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        return scene;
    }

}
