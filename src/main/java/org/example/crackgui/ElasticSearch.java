package org.example.crackgui;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElasticSearch {

    public String processInput(String input){

        String[] inputKeywords = input.split(" ");

        for(String s : inputKeywords){
            s.toLowerCase();
        }

        List<Documentation.FoundDocumentation> searchResults = getSearchResults();
        for(Documentation.FoundDocumentation d : searchResults){
            for(String s : inputKeywords){
                if(d.getKeywords().contains(s)){
                    System.out.println(d.getDocumentation());
                }
            }
        }
        return input;
    }

    public List<Documentation.FoundDocumentation> getSearchResults() {
        File folder = new File("src/main/resources/bijlagen");
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".json"));

        Gson gson = new Gson();
        List<Documentation.FoundDocumentation> combinedDocumentationList = new ArrayList<>();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                try (FileReader reader = new FileReader(file)) {
                    Type documentationType = new TypeToken<Documentation>(){}.getType();
                    Documentation documentation = gson.fromJson(reader, documentationType);

                    if (documentation != null && documentation.getFoundDocumentation() != null) {
                        combinedDocumentationList.addAll(documentation.getFoundDocumentation());
                    }

                } catch (IOException | JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

        return combinedDocumentationList;
    }
}
