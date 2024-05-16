package org.example.crackgui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class CrackGPTgui extends Application {
    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;
    private ResourceBundle resourceBundle;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        resourceBundle = ResourceBundle.getBundle("org.example.crackgui.messages", Locale.getDefault());
        Scene scene = createScene();
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setTitle("CrackGPT-4");
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene() {
        BorderPane root = new BorderPane();
        VBoxContainer mainContainer = new VBoxContainer(resourceBundle);

        root.setTop(new TopBar(root));
        root.setCenter(mainContainer);

        return new Scene(root, APP_WIDTH, APP_HEIGHT);
    }
}
