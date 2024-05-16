package org.example.crackgui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.util.ResourceBundle;

public class TalkButton extends StackPane {
    private Button talkButton;
    private ResourceBundle resourceBundle;
    private VBoxContainer mainContainer;

    public TalkButton(ResourceBundle resourceBundle, VBoxContainer mainContainer) {
        this.resourceBundle = resourceBundle;
        this.mainContainer = mainContainer;
        talkButton = new Button();
        talkButton.getStyleClass().add("talk-btn");
        talkButton.setMaxWidth(Double.MAX_VALUE);
        talkButton.setAlignment(Pos.CENTER);
        talkButton.setOnAction(event -> handleTalkButton());
        updateButton();

        setPadding(new Insets(40, 20, 0, 20));
        getChildren().add(talkButton);
    }

    private void handleTalkButton() {
        String userInput = mainContainer.getTextArea().getText();
        String selectedLanguage = mainContainer.getSelectedLanguage();

        CrackGPT crackGPT = new CrackGPT();
        crackGPT.crackGPT(userInput, selectedLanguage, new ResponseListener() {
            @Override
            public void onResponse(String response) {
                Platform.runLater(() -> {
                    mainContainer.getTextAreaOutput().appendText(response);
                });
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(String error) {
                Platform.runLater(() -> {
                    mainContainer.getTextAreaOutput().appendText("\nError: " + error + "\n");
                });
            }
        });
    }

    private void updateButton() {
        talkButton.setText(resourceBundle.getString("ask") + " CrackedGPT");
    }
}