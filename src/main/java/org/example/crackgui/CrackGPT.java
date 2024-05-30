package org.example.crackgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public BorderPane root = new BorderPane();
    private SettingsComponent settings;
    private PromptComponent prompt;
    private MenuComponent menuComponent = new MenuComponent(this);
    public ArrayList<HashMap<String, ArrayList<String>>> chats = new ArrayList<>();
    public ResourceBundle language;
    public ScrollPane menu;
    public int currentChat;

    public void start(Stage stage) throws IOException {
        this.language = ResourceBundle.getBundle("org.example.crackgui.messages", new Locale("nl"));

        // Initialize the settings component before calling prompScreen
        this.settings = new SettingsComponent(this);

        menuComponent.newChat();
        this.currentChat = 1;

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
        VBox header = createHeader();
        VBox bottomBox = createBottomBox();

        root.getStyleClass().add("body");

        root.setTop(header);
        root.setCenter(body);
        root.setBottom(bottomBox);

        return new Scene(root, width, height);
    }

    private VBox createBody() {
        VBox body = new VBox();

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

    private VBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setSpacing(10);

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


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        GridPane settingsDialog = settings.generate();
        settingsDialog.setAlignment(Pos.TOP_RIGHT);


        header.getChildren().addAll(hamburgerButton, spacer, settingsDialog);


        VBox headerBox = new VBox();
        headerBox.getChildren().add(header);

        return headerBox;
    }

    private VBox createBottomBox() {
        VBox bottomBox = new VBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 15, 20, 15));
        bottomBox.setSpacing(10);

        TextArea inputArea = prompt.generate();

        bottomBox.getChildren().addAll(inputArea);

        return bottomBox;
    }

    public SettingsComponent getSettings() {
        return this.settings;
    }

    public void update() {
        prompt.update();
    }

    public void updateMenu()
    {
        this.menu = menuComponent.generate(root.widthProperty().multiply(0.3));
        root.setLeft(this.menu);
    }
}
