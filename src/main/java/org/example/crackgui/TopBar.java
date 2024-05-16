package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class TopBar extends HBox {
    public TopBar(BorderPane root) {
        setAlignment(Pos.CENTER_LEFT);
        Button hamburgerButton = new Button("\u2630");
        hamburgerButton.getStyleClass().add("hamburger-button");
        hamburgerButton.setOnAction(event -> {
            if (root.getLeft() == null) {
                root.setLeft(createMenu());
            } else {
                root.setLeft(null);
            }
        });
        getChildren().add(hamburgerButton);
    }

    private HBox createMenu() {
        HBox menu = new HBox();
        menu.getStyleClass().add("menu");
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(10));
        menu.getChildren().addAll(new Label("New Chat"), new Label("History"));
        return menu;
    }
}
