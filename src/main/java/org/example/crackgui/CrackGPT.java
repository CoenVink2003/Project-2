package org.example.crackgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class CrackGPT extends Application {
    public BorderPane root;
    private SettingsComponent settings;
    private PromptComponent prompt;
    private MenuComponent menuComponent;
    public ArrayList<HashMap<String, ArrayList<String>>> chats = new ArrayList<>();
    public ResourceBundle language;
    public ScrollPane menu;
    public int currentChat;

    public void start(Stage stage) throws IOException {
        this.language = ResourceBundle.getBundle("org.example.crackgui.messages", new Locale("nl"));

        // Initialize the settings component before calling prompScreen
        this.settings = new SettingsComponent(this);

        Scene scene = createScene(800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("CrackGPT-4");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene(int width, int height) {
        VBox body = createBody();
        HBox header = createHeader();
        VBox bottomBox = createBottomBox();

        root = new BorderPane();
        root.setTop(header);
        root.setCenter(body);
        root.setBottom(bottomBox);

        return new Scene(root, width, height);
    }

    private VBox createBody() {
        VBox body = new VBox();
        body.setStyle("-fx-background-color: #2067DC;");
        body.getStyleClass().add("body");

        VBox chatContainer = new VBox();
        chatContainer.setPadding(new Insets(20, 15, 20, 15));
        chatContainer.setSpacing(10);

        ScrollPane scrollPane = new ScrollPane(chatContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        body.getChildren().addAll(scrollPane);

        prompt = new PromptComponent(this, chatContainer);

        return body;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10)); // Add padding for better appearance
        header.setSpacing(10);

        menuComponent = new MenuComponent(this);
        Button hamburgerButton = new Button("\u2630");
        hamburgerButton.getStyleClass().add("hamburger-button");
        hamburgerButton.setOnAction(event -> {
            if (root.getLeft() == null) {
                this.menu = menuComponent.generate(root.widthProperty().multiply(0.3));
                root.setLeft(this.menu);
            } else {
                root.setLeft(null);
            }
        });

        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);

        header.getChildren().addAll(hamburgerButton, leftSpacer, rightSpacer);

        return header;
    }

    private VBox createBottomBox() {
        VBox bottomBox = new VBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 15, 20, 15));
        bottomBox.setSpacing(10);

        GridPane settingsPane = settings.generate();
        TextArea inputArea = new TextArea();
        inputArea.setWrapText(true);
        inputArea.getStyleClass().add("input-bubble");

        Button talkButton = prompt.generate(inputArea, settings.getLanguage());

        bottomBox.getChildren().addAll(settingsPane, inputArea, talkButton);

        return bottomBox;
    }

    public SettingsComponent getSettings() {
        return this.settings;
    }

    public void update() {
        settings.update();
        prompt.update();

        this.menu = menuComponent.generate(root.widthProperty().multiply(0.3));
        root.setLeft(this.menu);
    }
}
