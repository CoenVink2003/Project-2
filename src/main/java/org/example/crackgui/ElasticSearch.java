package org.example.crackgui;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElasticSearch {
    private final String folderPath = "src/main/resources/bijlagen";
    private final Map<String, String> keywordInfoMap;
    private final List<JSONObject> documentationList;

    public ElasticSearch() {
        keywordInfoMap = new HashMap<>();
        documentationList = new ArrayList<>();
        loadDocumentation();
    }

    private void loadDocumentation() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    JSONTokener tokener = new JSONTokener(fis);
                    JSONObject jsonDocument = new JSONObject(tokener);

                    JSONArray foundDocumentation = jsonDocument.getJSONArray("foundDocumentation");

                    for (int i = 0; i < foundDocumentation.length(); i++) {
                        JSONObject documentationObject = foundDocumentation.getJSONObject(i);
                        JSONArray keywords = documentationObject.getJSONArray("keywords");
                        String documentation = documentationObject.getString("documentation");

                        // Add documentation to the list
                        documentationList.add(new JSONObject(documentation));

                        // Add each keyword-documentation pair to the map
                        for (int j = 0; j < keywords.length(); j++) {
                            keywordInfoMap.put(keywords.getString(j), documentation);
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.err.println("File not found: " + file.getAbsolutePath());
                    // Log the error or handle it according to your application's requirements
                }
            }
        }
    }

    public String processInput(String input) {
        StringBuilder query = new StringBuilder(input);
        query.append(" Maak gebruik van de gegeven informatie: ");
        boolean foundKeyword = false;

        for (String word : input.split("\\s+")) {
            String info = loadInfoByKeyword(word);
            if (!info.isEmpty()) {
                query.append(info).append(" ");
                foundKeyword = true;
            }
        }

        if (!foundKeyword) {
            query.append("No relevant information found for the input.");
        }

        return query.toString();
    }

    private String loadInfoByKeyword(String keyword) {
        return keywordInfoMap.getOrDefault(keyword.toLowerCase(), "");
    }

    public void displayDocumentation() {
        for (JSONObject documentation : documentationList) {
            System.out.println("Documentation: " + documentation.toString());
        }
    }

    public static void main(String[] args) {
        ElasticSearch elasticSearch = new ElasticSearch();
        elasticSearch.displayDocumentation();
    }
}
