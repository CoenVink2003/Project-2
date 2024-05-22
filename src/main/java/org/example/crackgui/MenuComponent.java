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

    private CrackGPT application;

    BorderPane root;
    public BorderPane getRoot() {
        return root;
    }

    public MenuComponent(CrackGPT application)
    {
        this.application = application;
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
        //tabPane.setVisible(false);
        ChatScene body = new ChatScene(application);

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
            //chatTabPane.setVisible(false);
            chatCounter++;
            String chatTitle = "Chat " + chatCounter;
            chatHistoryListView.getItems().add(chatTitle);

            ChatScene newBody = new ChatScene(application);

            Tab newTab = new Tab(chatTitle);
            newTab.setContent(newBody.createBody());
            chatTabPane.getTabs().add(newTab);
        });
        return button;
    }

    public void menuInitialize(){
        root= new BorderPane();

        chatHistoryListView = createChatHistoryListView();
        chatTabPane = createChatTabPane();
        Button newChatButton = createNewChatButton();

        HBox topMenu = new HBox();
        topMenu.setPadding(new Insets(10));
        //topMenu.setAlignment(Pos.CENTER);
        topMenu.getChildren().add(newChatButton);
/*
        VBox leftMenu = new VBox(chatHistoryListView);
        leftMenu.setPadding(new Insets(10));
        leftMenu.setSpacing(10);
        leftMenu.setPrefWidth(150);

        VBox mainContent = new VBox();
        mainContent.setSpacing(10);
        mainContent.setPadding(new Insets(10));
        VBox.setVgrow(chatTabPane, Priority.ALWAYS);

        mainContent.getChildren().addAll(topMenu, chatTabPane);

        HBox content = new HBox();
        content.getChildren().addAll(leftMenu, mainContent);
        root.setCenter(content);
 */
        root.setTop(topMenu);
        root.setCenter(chatTabPane);
        root.setLeft(chatHistoryListView);

    }


}


//////

