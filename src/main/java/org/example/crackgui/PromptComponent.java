package org.example.crackgui;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PromptComponent {
    private CrackGPT application;
    private static final String AI_URL = "http://localhost:11434/api/generate";

    public PromptComponent(CrackGPT application) {
        this.application = application;
    }

    public TextArea generate() {
        TextArea inputArea = new TextArea();
        inputArea.getStyleClass().add("promptInput");

        inputArea.setOnKeyPressed(this::handleKeyPressed);

        return inputArea;
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        TextArea inputArea = (TextArea) keyEvent.getSource();
        adjustTextAreaHeight(inputArea);
        if (keyEvent.getCode() == KeyCode.ENTER) {
            handleUserInput(inputArea);
        }
    }

    private void handleUserInput(TextArea inputArea) {
        String input = inputArea.getText().trim();
        if (!input.isEmpty()) {
            ElasticSearch elasticSearch = new ElasticSearch();
            String processedInput = elasticSearch.processInput(input);
            inputArea.clear();
            System.out.println(processedInput);
            application.getChatComponent().addBubble(true, input, true);
            promptAsync(processedInput); // Use processed input for AI prompt
        }
    }


    private void promptAsync(String input) {
        PromptService service = new PromptService(input);
        service.setOnSucceeded(event -> {
        });
        service.start();
    }

    private void adjustTextAreaHeight(TextArea textArea) {
        String text = textArea.getText();
        int numLines = text.split("\n").length;
        double lineHeight = textArea.getFont().getSize() * 1.2;
        double padding = 20;
        double newHeight = lineHeight * numLines + padding;
        textArea.setPrefHeight(newHeight);
    }

    private void addToOutputBubble(Label outputLabel, String chunk) {
        outputLabel.setText(outputLabel.getText() + convertStringToJson(chunk).getString("response"));
    }

    public static JSONObject convertStringToJson(String jsonString) {
        return new JSONObject(jsonString);
    }

    private class PromptService extends Service<Void> {
        private String input;

        public PromptService(String input) {
            this.input = input;
        }

        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    prompt(input);
                    return null;
                }
            };
        }

        private void prompt(String input) {
            try {
                HttpURLConnection con = createConnection(input);

                HBox outputBubble = application.getChatComponent().addBubble(false, "", false);

                processResponseStream(con, (Label) outputBubble.getChildren().getFirst());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private HttpURLConnection createConnection(String input) throws IOException {
            URL url = new URL(AI_URL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            JSONObject request = new JSONObject();
            request.put("model", "gemma");
            request.put("prompt", input + ". Antwoord in het " + application.getSettings().getLanguage());
            request.put("stream", true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] requestBody = request.toString().getBytes();
                os.write(requestBody, 0, requestBody.length);
            }

            return con;
        }

        private void processResponseStream(HttpURLConnection con, Label outputLabel) throws IOException {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    final String chunk = inputLine;
                    javafx.application.Platform.runLater(() -> addToOutputBubble(outputLabel, chunk));

                    if(convertStringToJson(chunk).getBoolean("done"))
                    {
                        application.getChatComponent().saveHistory( outputLabel.getText() + convertStringToJson(chunk).getString("response"));
                    }
                }
            }
        }
    }
}