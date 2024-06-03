package org.example.crackgui;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;

public class MenuComponent {
    private CrackGPT application;

    public MenuComponent(CrackGPT application) {
        this.application = application;
    }

    public ScrollPane generate(DoubleBinding width) {
        VBox menu = createMenuContainer();
        menu.getChildren().add(createNewChatButton());

        for (int i = 0; i < application.chats.size(); i++) {
            menu.getChildren().add(createChatHistoryButton(i));
        }

        return createScrollPane(menu, width);
    }

    private VBox createMenuContainer() {
        VBox menu = new VBox();
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(10));
        menu.setSpacing(10);
        return menu;
    }

    private Button createNewChatButton() {
        Button newItem = new Button("New Chat");
        newItem.setOnAction(event -> {
            newChat();
            application.updateMenu();
        });
        configureButton(newItem);
        return newItem;
    }

    private Button createChatHistoryButton(int index) {
        HashMap<String, ArrayList<String>> chat = application.chats.get(index);
        Button historyItem = new Button(chat.get("name").get(0));
        historyItem.setOnAction(event -> {
            this.application.currentChat = index + 1;
            application.getChatComponent().loadChat();
        });
        configureButton(historyItem);
        return historyItem;
    }

    private void configureButton(Button button) {
        button.getStyleClass().add("chatbtn");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setTextAlignment(TextAlignment.CENTER);
        button.setAlignment(Pos.CENTER);
    }

    private ScrollPane createScrollPane(VBox menu, DoubleBinding width) {
        ScrollPane menuRoot = new ScrollPane(menu);
        menuRoot.setFitToWidth(true);
        menuRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuRoot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        menuRoot.getStyleClass().add("menu-scrollbar");
        menuRoot.prefWidthProperty().bind(width);
        return menuRoot;
    }

    public void changeChat(int chatIndex) {
        application.currentChat = chatIndex;
        application.updateMenu();
    }

    public void newChat() {
        HashMap<String, ArrayList<String>> chatData = createNewChatData();
        application.chats.add(0, chatData);
    }

    private HashMap<String, ArrayList<String>> createNewChatData() {
        HashMap<String, ArrayList<String>> chatData = new HashMap<>();
        ArrayList<String> chatName = new ArrayList<>();
        int number = application.chats.size() + 1;
        chatName.add("Chat " + number);
        chatData.put("name", chatName);
        chatData.put("dialog", new ArrayList<>());
        return chatData;
    }
}
