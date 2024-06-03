package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsComponent {

    private CrackGPT application;
    private ComboBox<String> languageSelect;
    private String language;

    public SettingsComponent(CrackGPT application) {
        this.application = application;
        this.language = "Dutch";
        initializeComponents();
    }

    private void initializeComponents() {
        languageSelect = createLanguageComboBox();
        languageSelect.setOnAction(event -> updateLanguage());
    }

    private ComboBox<String> createLanguageComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("English", "Spanish", "Dutch", "Norwegian", "Portuguese", "German", "French", "Italian");
        comboBox.getStyleClass().add("setting-combo-box");
        comboBox.setValue("Dutch");
        return comboBox;
    }

    public GridPane generate() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 0, 0, 0));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(languageSelect, 0, 1);
        return gridPane;
    }

    private void updateLanguage() {
        String selectedLanguage = languageSelect.getValue();
        Locale newLocale = switchLocale(selectedLanguage);
        application.language = ResourceBundle.getBundle("org.example.crackgui.messages", newLocale);
    }

    private Locale switchLocale(String language) {
        this.language = languageSelect.getValue();
        return switch (language) {
            case "English" -> new Locale("en");
            case "Spanish" -> new Locale("es");
            case "Dutch" -> new Locale("nl");
            case "Norwegian" -> new Locale("no");
            case "Portuguese" -> new Locale("pt");
            case "German" -> new Locale("de");
            case "French" -> new Locale("fr");
            case "Italian" -> new Locale("it");
            default -> Locale.getDefault();
        };
    }

    public String getLanguage() {
        return this.language;
    }
}
