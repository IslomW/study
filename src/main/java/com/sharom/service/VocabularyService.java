package com.sharom.service;

import com.sharom.dto.CreateExampleRequest;
import com.sharom.dto.CreateExampleTranslationRequest;
import com.sharom.dto.CreateTranslationRequest;
import com.sharom.dto.CreateWordRequest;
import com.sharom.entity.Example;
import com.sharom.entity.ExampleTranslation;
import com.sharom.entity.Word;
import com.sharom.entity.WordTranslation;
import com.sharom.enums.PosType;

public interface VocabularyService {

    Word createWord(CreateWordRequest req);

    WordTranslation addTranslation(CreateTranslationRequest req);

    Example addExample(CreateExampleRequest req);

    ExampleTranslation addExampleTranslation(CreateExampleTranslationRequest req);

    WordTranslation updateTranslation(Long translationId, String newText);

    Example updateExample(Long exampleId, String newText);

    void deleteWord(Long wordId);

    void deleteTranslation(Long translationId);

    void deleteExample(Long exampleId);

    void deleteExampleTranslation(Long exampleTranslationId);
}
