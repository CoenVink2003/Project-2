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
    private BorderPane root;
    private SettingsComponent settings;
    private PromptComponent prompt;
    private MenuComponent menuComponent;
    public ArrayList<HashMap<String, ArrayList<String>>> chats;
    public ResourceBundle language;
    private ChatComponent chatComponent;
    private ScrollPane menu;
    public int currentChat;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setupLanguageBundle();
        initializeFields();
        setupNewChat();
        setupScene(stage);
    }

    private void initializeFields() {
        root = new BorderPane();
        chats = new ArrayList<>();
        menuComponent = new MenuComponent(this);
        settings = new SettingsComponent(this);
    }

    private void setupLanguageBundle() {
        language = ResourceBundle.getBundle("org.example.crackgui.messages", new Locale("nl"));
    }

    private void setupNewChat() {
        menuComponent.newChat();
        currentChat = 1;
    }

    private void setupScene(Stage stage) {
        Scene scene = createScene(800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("CrackGPT-4");
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private Scene createScene(int width, int height) {
        root.getStyleClass().add("body");

        root.setTop(createHeader());
        root.setCenter(createBody());
        root.setBottom(createBottomBox());

        return new Scene(root, width, height);
    }

    private VBox createBody() {
        VBox body = new VBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        VBox chatContainer = new VBox();
        chatContainer.setPadding(new Insets(20, 15, 20, 15));
        chatContainer.setSpacing(10);
        scrollPane.setContent(chatContainer);
        body.getChildren().add(scrollPane);

        chatComponent = new ChatComponent(this, chatContainer);
        prompt = new PromptComponent(this);

        return body;
    }

    private VBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));
        header.setSpacing(10);

        Button hamburgerButton = createHamburgerButton();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        GridPane settingsDialog = settings.generate();
        settingsDialog.setAlignment(Pos.TOP_RIGHT);

        header.getChildren().addAll(hamburgerButton, spacer, settingsDialog);

        VBox headerBox = new VBox();
        headerBox.getChildren().add(header);

        return headerBox;
    }

    private Button createHamburgerButton() {
        Button hamburgerButton = new Button("\u2630");
        hamburgerButton.getStyleClass().add("hamburger-button");
        hamburgerButton.setOnAction(event -> toggleMenuVisibility());
        return hamburgerButton;
    }

    private void toggleMenuVisibility() {
        if (root.getLeft() == null) {
            menu = menuComponent.generate(root.widthProperty().multiply(0.3));
            root.setLeft(menu);
        } else {
            root.setLeft(null);
        }
    }

    private VBox createBottomBox() {
        VBox bottomBox = new VBox();
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20, 15, 20, 15));
        bottomBox.setSpacing(10);

        TextArea inputArea = prompt.generate();

        bottomBox.getChildren().add(inputArea);

        return bottomBox;
    }

    public SettingsComponent getSettings() {
        return settings;
    }
    

    public void updateMenu() {
        menu = menuComponent.generate(root.widthProperty().multiply(0.3));
        root.setLeft(menu);
    }

    public ChatComponent getChatComponent()
    {
        return this.chatComponent;
    }
}
