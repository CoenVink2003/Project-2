package org.example.crackgui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsComponent {

    private CrackGPT application;
    private Label languageLabel;

    private String language;

    public SettingsComponent(CrackGPT application) {
        this.application = application;
    }

    public String getLanguage() {
        return this.language;
    }

    public GridPane generate() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 0, 0, 0));
        gridPane.setAlignment(Pos.CENTER);

        languageLabel = new Label("Language");
        languageLabel.getStyleClass().add("setting-label");
        GridPane.setHalignment(languageLabel, HPos.CENTER);
        gridPane.add(languageLabel, 0, 0);

        ComboBox<String> languageSelect = new ComboBox<>();
        languageSelect.getItems().addAll("English", "Spanish", "Dutch", "Norwegian", "Portuguese",
                "German", "French", "Italian");
        languageSelect.getStyleClass().add("setting-combo-box");
        gridPane.add(languageSelect, 0, 1);
        languageSelect.setValue("Dutch");

        languageSelect.setOnAction(event -> updateLanguage(languageSelect));

        return gridPane;
    }

    private void updateLanguage(ComboBox<String> languageSelect) {
        this.language = languageSelect.getValue();
        Locale newLocale;
        switch (this.language) {
            case "English":
                newLocale = new Locale("en");
                break;
            case "Spanish":
                newLocale = new Locale("es");
                break;
            case "Dutch":
                newLocale = new Locale("nl");
                break;
            case "Norwegian":
                newLocale = new Locale("no");
                break;
            case "Portuguese":
                newLocale = new Locale("pt");
                break;
            case "German":
                newLocale = new Locale("de");
                break;
            case "French":
                newLocale = new Locale("fr");
                break;
            case "Italian":
                newLocale = new Locale("it");
                break;
            default:
                newLocale = Locale.getDefault();
                break;
        }

        // Set the new language
        this.application.language = ResourceBundle.getBundle("org.example.crackgui.messages", newLocale);

        // Update all texts to the new language
        this.application.update();
    }

    public void update() {
        this.languageLabel.setText(this.application.language.getString("language"));
    }
}
