package org.example.crackgui;


public class ElasticSearch {

    private final String fileName = "Documentation.json";
    private Map<String, String> keywordInfoMap;

public void InputT(String input){

        StringBuilder query = new StringBuilder(input);
        query.append(" ((Beantwoord de vraag in het \" + language + \" en gebruik alleen de gegeven informatie (als die er is; als die ontbreekt, verzin dan zelf iets): ");
        boolean foundKeyword = false;

        ReadingFile readingFile = new ReadingFile();
        for(String word : input.split("\\s+")){
            String info = readingFile.loadInfoByKeyword(word);
            if(!info.isEmpty()){
                query.append(info).append(" ");
                foundKeyword = true;
            }
        }

        if (foundKeyword) {
            query.append(" ) ");
        } else {
            query.append(" geen relevente keyword gevonden )");
        }
       // return query.toString();
        this.input = query.toString();
    }


    public ReadingFile() {
        keywordInfoMap = new HashMap<>();
        loadKeywordInfo();
    }

    private void loadKeywordInfo() {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            JSONTokener tokener = new JSONTokener(fis);
            JSONArray jsonArray = new JSONArray(tokener);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String keyword = jsonObject.getString("Keyword").replaceAll("[?,.!]", "");
                String info = jsonObject.getString("Info");
                keywordInfoMap.put(keyword.toLowerCase(), info);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadInfoByKeyword(String keyword) {
        keyword = keyword.replaceAll("[?,.!]", "").toLowerCase();
        return keywordInfoMap.getOrDefault(keyword, "");
    }
}
