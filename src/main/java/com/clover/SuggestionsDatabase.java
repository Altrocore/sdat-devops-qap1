package com.clover;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class SuggestionsDatabase {
    private Map<String,Integer> wordMap = new HashMap<>();

    public Map<String, Integer> getWordMap() {
        if (wordMap == null) {
            wordMap = new HashMap<String, Integer>();
        }

        return wordMap;
    }

    public void setWordMap(Map<String, Integer> wordMap) {
        this.wordMap = wordMap;
    }
}