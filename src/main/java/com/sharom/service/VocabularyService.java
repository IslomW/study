package com.sharom.service;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.VocabularyWord;
import com.sharom.entity.VocabularyWordTranslation;
import com.sharom.enums.PosType;

public interface VocabularyService {

    VocabularyWord createWord(String word, PosType posType, Long levelId);

    VocabularyWordTranslation addTranslation(Long wordId, String lang, String translationText);

    ExamplePair addExample(Long translationId, String exampleText);

    VocabularyWordTranslation updateTranslation(Long translationId, String newText);

    ExamplePair updateExample(Long exampleId, String newText);

    void deleteWord(Long wordId);

    void deleteTranslation(Long translationId);

    void deleteExample(Long exampleId);
}
