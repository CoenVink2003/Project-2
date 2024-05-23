package org.example.crackgui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PromptComponent {
    private CrackGPT application;
    private Button talkButton;

    private static final String ai_url = "http://localhost:11434/api/generate";

    public PromptComponent(CrackGPT application)
    {
        this.application = application;
    }

    public Button generate(TextArea inputTextArea, TextArea outputTextArea)
    {
        Button button = new Button();
        button.getStyleClass().add("talk-btn");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER);


        String buttonText = this.application.language.getString("ask") + " CrackedGPT";
        button.setText(buttonText);


        button.setOnAction(event -> {
            String input = inputTextArea.getText();

            String response = prompt(input);

            outputTextArea.setText(response);
        });

        this.talkButton = button;
        return button;
    }

    public void update()
    {
        this.talkButton.setText(this.application.language.getString("ask") + " CrackedGPT");
    }

    public String prompt(String input)
    {
        try {
            String prompt = input + "\nPlease answer everything in " + this.application.getSettings().getLanguage() + ".";

            // Prepare the request
            JSONObject request = new JSONObject();
            request.put("model", "gemma");
            request.put("prompt", prompt);
            request.put("stream", false);
            URL url = new URL(ai_url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);

            try (OutputStream os = con.getOutputStream()) {
                byte[] requestBody = request.toJSONString().getBytes();
                os.write(requestBody, 0, requestBody.length);
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        response.append(inputLine);
                    }

                    System.out.println(response);
                    return parseResponse(response.toString());
                }
            } else {
                try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    StringBuilder errorMessage = new StringBuilder();
                    String errorLine;

                    while ((errorLine = errorReader.readLine()) != null) {
                        errorMessage.append(errorLine);
                    }

                    return "Server responded with status code " + responseCode + ": " + errorMessage.toString();
                }
            }
        } catch (IOException | ParseException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String parseResponse(String response) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonResponse = (JSONObject) parser.parse(response);
        Object responseObject = jsonResponse.get("response");

        if (responseObject != null) {
            System.out.println(responseObject.toString());
            return responseObject.toString();
        } else {
            return "Server response is missing or invalid.";
        }
    }
}
