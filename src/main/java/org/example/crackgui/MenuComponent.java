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

    public MenuComponent(CrackGPT application)
    {
        this.application = application;
    }

    public ScrollPane generate(DoubleBinding width) {
        VBox menu = new VBox();
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(10));
        menu.setSpacing(10);

        Button newItem = new Button("New Chat");
        newItem.setOnAction(event -> {
            newChat();
            this.application.updateMenu();
        });
        newItem.getStyleClass().add("chatbtn");
        newItem.setMaxWidth(Double.MAX_VALUE);
        newItem.setTextAlignment(TextAlignment.CENTER);
        newItem.setAlignment(Pos.CENTER);
        menu.getChildren().add(newItem);

        for (int i = 0; i < this.application.chats.size(); i++) {
            HashMap<String, ArrayList<String>> chat = this.application.chats.get(i);
            Button historyItem = new Button(chat.get("name").get(0));
            final int index = i; // Capture the index
            historyItem.setOnAction(event -> changeChat(index));
            historyItem.setMaxWidth(Double.MAX_VALUE);
            historyItem.getStyleClass().add("chatbtn");
            menu.getChildren().add(historyItem);
        }


        ScrollPane menuRoot = new ScrollPane(menu);
        menuRoot.setFitToWidth(true);
        menuRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        menuRoot.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        menuRoot.getStyleClass().add("menu-scrollbar");
        menuRoot.prefWidthProperty().bind(width);
        return menuRoot;
    }

    public void changeChat(int chatIndex)
    {
        this.application.currentChat = chatIndex;
        this.application.updateMenu();
    }

    public void newChat() {
        HashMap<String, ArrayList<String>> chatData = new HashMap<String, ArrayList<String>>();
        ArrayList<String> chatNaam = new ArrayList<String>();
        int number = this.application.chats.size() + 1;
        chatNaam.add("Chat " + number);
        chatData.put("name", chatNaam);
        chatData.put("dialog", new ArrayList<String>());
        this.application.chats.addFirst(chatData);
    }
}
