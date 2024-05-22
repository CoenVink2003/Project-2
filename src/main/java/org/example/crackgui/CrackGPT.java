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
    private SettingsComponent settings;
    private PromptComponent prompt;

    MenuComponent menu = new MenuComponent(this);
    BorderPane root = new BorderPane();

    public ResourceBundle language;

    public void start(Stage stage) throws IOException {
        this.language =  ResourceBundle.getBundle("org.example.crackgui.messages", new Locale("nl"));
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
        root = new BorderPane();
        ChatScene body = new ChatScene(this);

        root.setCenter(body.createBody());
        root.setTop(menuButton());

        Scene scene = new Scene(root,800, 600);
        return scene;
    }

    public HBox menuButton(){
        // Root layout
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        // Menu
        Button hamburgerButton = new Button("\u2630");
        hamburgerButton.getStyleClass().add("hamburger-button");
        hamburgerButton.setOnAction(event -> {
            if (root.getLeft() == null) {
                root.setLeft(menu.getRoot());
            } else {
                root.setLeft(null);
            }
        });
        header.getChildren().add(hamburgerButton);

        return header;
    }

    public SettingsComponent getSettings()
    {
        return this.settings;
    }

    public void update() {
        settings.update();
        prompt.update();
    }
}
