package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class MenuComponent {
    private CrackGPT application;

    public MenuComponent(CrackGPT application)
    {
        this.application = application;
    }

    public HBox generate() {
        HBox menu = new HBox();
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10));

        Label newItem = new Label("New Chat");
        Label historyItem = new Label("History");
        menu.getChildren().addAll(newItem, historyItem);

        return menu;
    }
}
