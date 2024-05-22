package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ChatScene {
    private TextArea textArea;
    private TextArea textAreaOutput;
    private Button talkButton;
    private SettingsComponent settings;
    private PromptComponent prompt;
    private CrackGPT application;

    public ChatScene(CrackGPT application) {
        this.application = application;
        textArea = new TextArea();
        textAreaOutput = new TextArea();
        talkButton = new Button();
    }

    public VBox createBody(){

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
        settings = new SettingsComponent(application);
        GridPane settingsPane = settings.generate();

        // AI output area
        TextArea outputArea = new TextArea();
        outputArea.setWrapText(true);
        outputArea.setEditable(false);
        StackPane textAreaOutputPane = new StackPane();
        textAreaOutputPane.setPadding(new Insets(20, 15, 20, 15));
        textAreaOutputPane.getChildren().add(outputArea);

        // Prompt button
        prompt = new PromptComponent(application);
        Button talkButton = prompt.generate(inputArea, settings.getLanguage(), outputArea);
        StackPane talkButtonPane = new StackPane();
        talkButtonPane.setPadding(new Insets(40, 20, 0, 20));
        talkButtonPane.getChildren().add(talkButton);


        // Add elements to the body
        body.getChildren().addAll(title, inputAreaPane, settingsPane, talkButtonPane, textAreaOutputPane);


        return body;
    }
}


