package com.sharom.service;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.Level;
import com.sharom.entity.PosType;
import com.sharom.entity.VocabularyWord;

import java.util.List;
import java.util.Map;

public interface VocabularyService {

    VocabularyWord addWord(VocabularyWord word);

    VocabularyWord addSentenceById(Long id, List<ExamplePair> sentences);

    VocabularyWord updateWord(Long id, VocabularyWord word);

    VocabularyWord getWordById(Long id);

    List<VocabularyWord> getAllWordsFiltered(Level level, PosType posType, int page, int size, String sortBy);

    VocabularyWord getRandomWordByLevel(Level level);

    void deleteExampleSentence(Long exampleId);

    void deleteWord(Long wordId);
}
