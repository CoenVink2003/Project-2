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

    public ResourceBundle language;

    public void start(Stage stage) throws IOException {
        this.language =  ResourceBundle.getBundle("org.example.crackgui.messages", new Locale("nl"));;
        Scene scene = prompScreen(800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("CrackGPT-4");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private Scene prompScreen(int width, int height) {
        // Background container
        VBox body = new VBox();
        body.setStyle("-fx-background-color: #2067DC;");
        body.getStyleClass().add("body");

        // Header label
        Label title = new Label("CrackGPT-4");
        title.getStyleClass().add("crackgpt-label");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setAlignment(Pos.CENTER);

        // Input area
        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);
        StackPane inputAreaPane = new StackPane();
        inputAreaPane.setPadding(new Insets(0, 15, 0, 15));
        inputAreaPane.getChildren().add(inputArea);

        // Settings screen
        settings = new SettingsComponent(this);
        GridPane settingsPane = settings.generate();

        // AI output area
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        StackPane textAreaOutputPane = new StackPane();
        textAreaOutputPane.setPadding(new Insets(20, 15, 20, 15));
        textAreaOutputPane.getChildren().add(outputArea);

        // Prompt button
        prompt = new PromptComponent(this);
        Button talkButton = prompt.generate(inputArea, outputArea);
        StackPane talkButtonPane = new StackPane();
        talkButtonPane.setPadding(new Insets(40, 20, 0, 20));
        talkButtonPane.getChildren().add(talkButton);

        // Root layout
        BorderPane root = new BorderPane();
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        // Menu
        MenuComponent menu = new MenuComponent(this);
        Button hamburgerButton = new Button("\u2630");
        hamburgerButton.getStyleClass().add("hamburger-button");
         hamburgerButton.setOnAction(event -> {
             if (root.getLeft() == null) {
                 root.setLeft(menu.generate());
             } else {
                 root.setLeft(null);
             }
         });
        header.getChildren().add(hamburgerButton);

        // Add elements to the body
        body.getChildren().addAll(title, inputAreaPane, settingsPane, talkButtonPane, textAreaOutputPane);

        // Set elements in root layout
        root.setTop(header);
        root.setCenter(body);

        // Create and return the scene
        Scene scene = new Scene(root, width, height);
        return scene;
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
