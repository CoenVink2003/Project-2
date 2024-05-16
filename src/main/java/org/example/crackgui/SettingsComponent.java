package org.example.crackgui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsComponent extends GridPane {
    private ComboBox<String> language;
    private Label languageLabel;
    private ResourceBundle resourceBundle;

    public SettingsComponent(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        setPadding(new Insets(10, 0, 0, 0));
        setAlignment(Pos.CENTER);

        languageLabel = new Label(resourceBundle.getString("language"));
        languageLabel.getStyleClass().add("setting-label");
        GridPane.setHalignment(languageLabel, HPos.CENTER);
        add(languageLabel, 0, 0);

        language = new ComboBox<>();
        language.getItems().addAll("English", "Spanish", "Dutch", "Norwegian", "Portuguese", "German", "French", "Italian");
        language.getStyleClass().add("setting-combo-box");
        language.setValue("Dutch");
        language.setOnAction(event -> languageChange());
        add(language, 0, 1);
    }

    public String getSelectedLanguage() {
        return language.getValue();
    }

    private void languageChange() {
        Locale newLocale;
        switch (language.getValue()) {
            case "English": newLocale = new Locale("en"); break;
            case "Spanish": newLocale = new Locale("es"); break;
            case "Dutch": newLocale = new Locale("nl"); break;
            case "Norwegian": newLocale = new Locale("no"); break;
            case "Portuguese": newLocale = new Locale("pt"); break;
            case "German": newLocale = new Locale("de"); break;
            case "French": newLocale = new Locale("fr"); break;
            case "Italian": newLocale = new Locale("it"); break;
            default: newLocale = Locale.getDefault(); break;
        }
        resourceBundle = ResourceBundle.getBundle("org.example.crackgui.messages", newLocale);
        updateTexts();
    }

    private void updateTexts() {
        languageLabel.setText(resourceBundle.getString("language"));
    }
}
