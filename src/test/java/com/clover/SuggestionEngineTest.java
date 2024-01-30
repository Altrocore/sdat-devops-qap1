package com.clover;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SuggestionEngineTest {
    @InjectMocks
    private SuggestionEngine suggestionEngine = new SuggestionEngine();

    @Mock
    private SuggestionsDatabase mockSuggestionDB;
    private boolean testInstanceSame = false;

    @Test
    public void testEmptyInput() {
        String result = suggestionEngine.generateSuggestions("");
        Assertions.assertTrue(result.isEmpty(), "Empty input => no suggestions.");
    }

    @Test
    public void testNullInput() {
        String result = suggestionEngine.generateSuggestions(null);
        Assertions.assertTrue(result.isEmpty(), "Null input => no suggestions.");
    }

    @Test
    public void testUnusualCharacters() {
        Map<String, Integer> wordMapForTest = new HashMap<>();
        wordMapForTest.put("hello", 1);
        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        String result = suggestionEngine.generateSuggestions("@£$%");
        Assertions.assertTrue(result.isEmpty(), "Input with unusual characters => no suggestions due to no matches.");
    }

    @Test
    public void testCorrectButRareWord() {
        Map<String, Integer> rareWordMap = new HashMap<>();
        rareWordMap.put("abracadabra", 1);
        when(mockSuggestionDB.getWordMap()).thenReturn(rareWordMap);

        String result = suggestionEngine.generateSuggestions("abracadabra");
        Assertions.assertTrue(result.isEmpty(), "Correct but rare words should not produce suggestions assuming it's typed intentionally.");
    }

    @Test
    public void testSuggestionLimit() {
        Map<String, Integer> wordMapForLimitTest = new HashMap<>();
        for (int i = 1; i <= 20; i++) {
            wordMapForLimitTest.put("hello" + i, i);
        }
        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForLimitTest);

        String result = suggestionEngine.generateSuggestions("hello");

        int suggestionCount = 0;
        if (!result.isEmpty()) {
            suggestionCount = result.split("\n").length;
        }
        Assertions.assertTrue(suggestionCount <= 10, "Suggestion list should be limited to at most 10 entries.");
    }

    @Test
    public void testSuggestionsOrdering() {
        Map<String, Integer> wordMapForTest = new HashMap<>();
        wordMapForTest.put("hello", 10);
        wordMapForTest.put("hell", 5);
        wordMapForTest.put("helloes", 3);
        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        String result = suggestionEngine.generateSuggestions("hellw");
        String firstSuggestion = result.split("\n")[0];

        Assertions.assertEquals("hell", firstSuggestion, "The most likely suggestion should appear first in the list.");
    }

    @Test
    public void testWordWithMultiplePossibleCorrections() {
        Map<String, Integer> wordMapForTest = new HashMap<>();
        wordMapForTest.put("led", 1);
        wordMapForTest.put("lot", 1);
        wordMapForTest.put("lol", 1);
        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        String result = suggestionEngine.generateSuggestions("les");
        Assertions.assertTrue(result.contains("led") && result.contains("lot") && result.contains("lol"),
                "Should suggest all possible corrections for a typo.");
    }

    @Test
    public void testNonexistentWord() {
        when(mockSuggestionDB.getWordMap()).thenReturn(Map.of());

        String result = suggestionEngine.generateSuggestions("ntn");
        Assertions.assertTrue(result.isEmpty(), "Nonexistent words without close matches should yield no suggestions.");
    }


    @Test
    public void testGenerateSuggestions() throws Exception {
        Map<String,Integer> wordMapForTest = new HashMap<>();

        wordMapForTest.put("hello", 1);

        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        Assertions.assertTrue(suggestionEngine.generateSuggestions("hellw").contains("hello"));
    }

    @Test
    public void testGenerateSuggestionsFail() {
        Map<String, Integer> wordMap = new HashMap<>();
        wordMap.put("hello", 1);
        when(mockSuggestionDB.getWordMap()).thenReturn(wordMap);

        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        String result = suggestionEngine.generateSuggestions("hello");

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    public void testSuggestionsAsMock() {
        Map<String,Integer> wordMapForTest = new HashMap<>();

        wordMapForTest.put("test", 1);

        when(mockSuggestionDB.getWordMap()).thenReturn(wordMapForTest);

        suggestionEngine.setWordSuggestionDB(mockSuggestionDB);

        Assertions.assertFalse(suggestionEngine.generateSuggestions("test").contains("test"));

        Assertions.assertTrue(suggestionEngine.generateSuggestions("tes").contains("test"));
    }
}

//test CI