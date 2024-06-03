package org.example.crackgui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class ChatComponent {
    private VBox chatContainer;
    private CrackGPT application;

    public ChatComponent(CrackGPT application, VBox chatContainer) {
        this.application = application;
        this.chatContainer = chatContainer;
    }


    public HBox addBubble(boolean input, String text, boolean save) {
        Label label = new Label(text);
        HBox newBubble = new HBox(label);

        if(save)
        {
            this.saveHistory(input, text);
        }

        Runnable addBubbleTask = () -> {
            if (input) {
                label.getStyleClass().add("input-bubble");
                newBubble.setAlignment(Pos.CENTER_RIGHT);
            } else {
                label.getStyleClass().add("output-bubble");
                newBubble.setAlignment(Pos.CENTER_LEFT);
            }

            chatContainer.getChildren().add(newBubble);
        };

        if (Platform.isFxApplicationThread()) {
            addBubbleTask.run();
        } else {
            Platform.runLater(addBubbleTask);
        }

        return newBubble;
    }

    public void saveHistory(boolean input, String text)
    {
        ArrayList<String> chatHistory = this.application.chats.get(this.application.currentChat - 1).get("dialog");

        chatHistory.add(text);
    }

    public void loadChat()
    {
        // Create a snapshot of the chat history to avoid concurrent modification
        ArrayList<String> chatHistorySnapshot;
        synchronized (this.application.chats) {
            chatHistorySnapshot = new ArrayList<>(this.application.chats.get(this.application.currentChat - 1).get("dialog"));
        }
        Platform.runLater(() -> {
            this.chatContainer.getChildren().clear();
            int loop_index = 0;
            for (String message : chatHistorySnapshot) {
                if (loop_index % 2 == 0) {
                    this.addBubble(true, message, false);
                } else {
                    this.addBubble(false, message, false);
                }
                loop_index++;
            }
        });
    }


}
