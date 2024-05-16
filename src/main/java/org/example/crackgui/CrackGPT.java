package org.example.crackgui;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class CrackGPT {

    private static final String API_URL = "http://localhost:11434/api/generate";
    private static final String CONTENT_TYPE_JSON = "application/json";

    public void crackGPT(String userInput, String selectedLanguage, ResponseListener listener) {
        try {
            String prompt = createPrompt(userInput, selectedLanguage);
            JSONObject requestBody = createRequestBody(prompt);
            HttpURLConnection connection = setupConnection();

            sendRequest(connection, requestBody);
            readResponse(connection.getInputStream(), listener);

        } catch (IOException e) {
            listener.onError("Error: " + e.getMessage());
        }
    }

    private String createPrompt(String userInput, String selectedLanguage) {
        return userInput + "\nPlease answer everything in " + selectedLanguage + ".";
    }

    private JSONObject createRequestBody(String prompt) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gemma");
        requestBody.put("prompt", prompt);
        requestBody.put("stream", true);  // Set to true for streaming responses
        return requestBody;
    }

    private HttpURLConnection setupConnection() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
        connection.setDoOutput(true);
        return connection;
    }

    private void sendRequest(HttpURLConnection connection, JSONObject requestBody) throws IOException {
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.toJSONString().getBytes();
            os.write(input, 0, input.length);
        }
    }

    private void readResponse(InputStream inputStream, ResponseListener listener) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            JSONParser parser = new JSONParser();

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);  // Print each line as it comes in for real-time feedback
                JSONObject jsonResponse = (JSONObject) parser.parse(inputLine);
                listener.onResponse((String) jsonResponse.get("response"));

                if ((Boolean) jsonResponse.get("done")) {
                    listener.onComplete();
                    break;
                }
            }
        } catch (IOException | ParseException e) {
            listener.onError("Error: " + e.getMessage());
        }
    }
}