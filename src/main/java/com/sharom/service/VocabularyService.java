package com.sharom.service;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.VocabularyWord;

import java.util.List;

public interface VocabularyService {

    VocabularyWord addWord(VocabularyWord word);

    VocabularyWord addSentenceById(Long id, List<ExamplePair> sentences);

    VocabularyWord updateWord(Long id, VocabularyWord word);

    VocabularyWord getWordById(Long id);

    List<VocabularyWord> getAllWordsFiltered(int level, String posType, int page, int size, String sortBy);

    VocabularyWord getRandomWordByLevel(int level);

    void deleteExampleSentence(Long exampleId);

    void deleteWord(Long wordId);
}
