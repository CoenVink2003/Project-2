package org.example.crackgui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MenuComponent {

    TabPane chatTabPane;
    int chatCounter = 1;
    private ListView<String> chatHistoryListView;



    BorderPane root;
    public BorderPane getRoot() {
        return root;
    }




    private ListView<String> createChatHistoryListView(){
        ListView<String> listView = new ListView<>();
        listView.getItems().add("Chat 1");


        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null){
                for(Tab t : chatTabPane.getTabs()) {
                    if(t.getText().equals(newValue)) {
                        chatTabPane.getSelectionModel().select(t);
                        break;
                    }
                }
            }
        });
        listView.setPrefWidth(140);
        return listView;
    }

    private TabPane createChatTabPane(){
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("hidden-tab-header");
        ChatScene body = new ChatScene();

        Tab tab = new Tab("Chat 1");
        tab.setContent(body.createBody());
        tabPane.getTabs().add(tab);

        tabPane.setTabMaxHeight(0);
        tabPane.setTabMinHeight(0);

        return tabPane;
    }

    private Button createNewChatButton(){

        Button button = new Button("New Chat");
        button.setOnAction(e -> {
            chatCounter++;
            String chatTitle = "Chat " + chatCounter;
            chatHistoryListView.getItems().add(chatTitle);

            ChatScene newBody = new ChatScene();

            Tab newTab = new Tab(chatTitle);
            newTab.setContent(newBody.createBody());
            chatTabPane.getTabs().add(newTab);

            chatTabPane.getStyleClass().add("hidden-tab-headers");
        });
        return button;
    }

    public ToggleButton toggleButton(){
        ToggleButton toggleHistoryButton = new ToggleButton("Toggle Chat History");
        toggleHistoryButton.setSelected(true);
        toggleHistoryButton.setOnAction(e -> {
            if (toggleHistoryButton.isSelected()) {
                root.setLeft(chatHistoryListView);
            } else {
                root.setLeft(null);
            }
        });
        return toggleHistoryButton;
    }


    public void menuInitialize(){

        if(root == null) {
            root= new BorderPane();

            chatHistoryListView = createChatHistoryListView();
            chatTabPane = createChatTabPane();
            Button newChatButton = createNewChatButton();

            HBox topMenu = new HBox();
            topMenu.setPadding(new Insets(10));
            //topMenu.setAlignment(Pos.CENTER);
            topMenu.getChildren().add(newChatButton);
            topMenu.getChildren().add(toggleButton());

            root.setTop(topMenu);
            root.setCenter(chatTabPane);
            root.setLeft(chatHistoryListView);

            chatTabPane.getSelectionModel().select(0);


        }
    }


}


//////

