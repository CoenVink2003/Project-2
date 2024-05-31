module org.example.crackgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires json.simple;
    requires org.json;
    requires com.google.gson;
    requires javafx.base;
    requires javafx.graphics;

    exports org.example.crackgui;
    opens org.example.crackgui to com.google.gson, javafx.fxml;
}