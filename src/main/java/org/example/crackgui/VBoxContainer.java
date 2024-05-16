package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class VBoxContainer extends VBox {
    private TextArea textArea;
    private TextArea textAreaOutput;
    private SettingsComponent settingsComponent;
    private ResourceBundle resourceBundle;

    public VBoxContainer(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        setStyle("-fx-background-color: #2067DC;");
        getStyleClass().add("body");

        settingsComponent = new SettingsComponent(resourceBundle);

        getChildren().addAll(
                createTitleLabel(),
                createTextArea(),
                settingsComponent,
                new TalkButton(resourceBundle, this),
                createTextAreaOutput()
        );
    }

    private Label createTitleLabel() {
        Label titleLabel = new Label("TimoOnCrack snif snif");
        titleLabel.getStyleClass().add("crackgpt-label");
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleLabel.setAlignment(Pos.CENTER);
        return titleLabel;
    }

    private StackPane createTextArea() {
        textArea = new TextArea();
        textArea.setWrapText(true);
        StackPane textAreaPane = new StackPane(textArea);
        textAreaPane.setPadding(new Insets(0, 15, 0, 15));
        return textAreaPane;
    }

    private StackPane createTextAreaOutput() {
        textAreaOutput = new TextArea();
        textAreaOutput.setWrapText(true);
        textAreaOutput.setEditable(false);
        StackPane textAreaOutputPane = new StackPane(textAreaOutput);
        textAreaOutputPane.setPadding(new Insets(20, 15, 20, 15));
        return textAreaOutputPane;
    }

    public TextArea getTextArea() {
        return textArea;
    }

    public TextArea getTextAreaOutput() {
        return textAreaOutput;
    }

    public String getSelectedLanguage() {
        return settingsComponent.getSelectedLanguage();
    }
}