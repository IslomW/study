package com.sharom.service;

import com.sharom.dto.*;
import com.sharom.entity.*;

public interface VocabularyService {

    // ------------------- Words -------------------

    Word createWord(CreateWordRequest req);

    Word getWordById(Long id);

    PageResponse<Word> getAllWords(int page, int size);

    Word updateWord(Long wordId, CreateWordRequest req);

    WordTranslation addTranslation(CreateTranslationRequest req);

    WordTranslation updateTranslation(Long translationId, String newText);

    void deleteWord(Long wordId);

    void deleteTranslation(Long translationId);

    Language addLanguage(CreateLangRequest req);

    // ------------------- Example -------------------

    Example addExample(CreateExampleRequest req);

    Example updateExample(Long exampleId, String newText);

    ExampleTranslation addExampleTranslation(CreateExampleTranslationRequest req);

    ExampleTranslation updateExampleTranslation(Long exampleTranslationId, String newText);

    void deleteExample(Long exampleId);

    void deleteExampleTranslation(Long exampleTranslationId);
}
