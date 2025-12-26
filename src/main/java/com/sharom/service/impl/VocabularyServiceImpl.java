package com.sharom.service.impl;

import com.sharom.dto.*;
import com.sharom.entity.*;
import com.sharom.enums.PosType;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.*;
import com.sharom.service.VocabularyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class VocabularyServiceImpl implements VocabularyService {

    private final WordRepository wordRepository;
    private final WordTranslationRepository translationRepository;
    private final ExampleRepository exampleRepository;
    private final ExampleTranslationRepository exampleTranslationRepository;
    private final LevelRepository levelRepository;
    private final LanguageRepository languageRepository;

    @Inject
    public VocabularyServiceImpl(WordRepository wordRepository, WordTranslationRepository translationRepository, ExampleRepository exampleRepository, ExampleTranslationRepository exampleTranslationRepository, LevelRepository levelRepository, LanguageRepository languageRepository) {
        this.wordRepository = wordRepository;
        this.translationRepository = translationRepository;
        this.exampleRepository = exampleRepository;
        this.exampleTranslationRepository = exampleTranslationRepository;
        this.levelRepository = levelRepository;
        this.languageRepository = languageRepository;
    }

    // ------------------- Words -------------------

    @Override
    @Transactional
    public Word createWord(CreateWordRequest req) {
        Level level = levelRepository.findByIdOptional(req.levelId())
                .orElseThrow(BadRequestException::levelNotFound);

        Language language = languageRepository.findByCode(req.lang().toUpperCase())
                .orElseThrow(BadRequestException::dataNotFound);

        Word vw = new Word();
        vw.setText(req.word());
        vw.setLanguage(language);
        vw.setLevel(level);
        vw.setPosType(req.posType());

        wordRepository.persist(vw);
        return vw;
    }

    @Override
    @Transactional
    public WordTranslation addTranslation(CreateTranslationRequest req) {
        Word word = wordRepository.findByIdOptional(req.wordId())
                .orElseThrow(BadRequestException::wordNotFound);

        Language language = languageRepository.findByCode(req.lang().toUpperCase())
                .orElseThrow(BadRequestException::dataNotFound);

        // check
        if (translationRepository.existsByWordAndLanguage(word, language)) {
            throw BadRequestException.translationAlreadyExists();
        }

        WordTranslation translation = new WordTranslation();
        translation.setWord(word);
        translation.setLanguage(language);
        translation.setText(req.translation());

        translationRepository.persist(translation);

        return translation;
    }


    @Override
    @Transactional
    public Word updateWord(Long wordId, CreateWordRequest req) {
        Word exists = wordRepository.findByIdOptional(wordId)
                .orElseThrow(BadRequestException::wordNotFound);

        exists.setText(Optional.ofNullable(req.word()).orElse(exists.getText()));


        return exists;
    }

    @Override
    @Transactional
    public WordTranslation updateTranslation(Long translationId, String newText) {
        WordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);

        translation.setText(newText);

        return translation;
    }


    @Override
    public Word getWordById(Long id) {
        return wordRepository.findByIdOptional(id)
                .orElseThrow(BadRequestException::wordNotFound);
    }

    @Override
    public PageResponse<Word> getAllWords(int page, int size) {
        List<Word> words = wordRepository.findAllPaged(page, size);
        long total = wordRepository.count();
        return new PageResponse<>(words, page, size, total);
    }


    @Override
    @Transactional
    public void deleteWord(Long wordId) {
        Word word = wordRepository.findByIdOptional(wordId)
                .orElseThrow(BadRequestException::wordNotFound);
        wordRepository.delete(word);
    }

    @Override
    @Transactional
    public void deleteTranslation(Long translationId) {
        WordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);
        translationRepository.delete(translation);
    }

    @Override
    @Transactional
    public Language addLanguage(CreateLangRequest req) {

        Language language = new Language();

        language.setCode(req.code().toUpperCase());
        language.setName(req.name());

        languageRepository.persist(language);
        return language;
    }


    // ------------------- Example -------------------

    @Override
    @Transactional
    public Example addExample(CreateExampleRequest req) {

        Word word = wordRepository.findByIdOptional(req.wordId())
                .orElseThrow(BadRequestException::wordNotFound);

        Example example = new Example();
        example.setText(req.text());
        example.setWord(word);

        exampleRepository.persist(example);

        return example;
    }

    @Override
    @Transactional
    public ExampleTranslation addExampleTranslation(CreateExampleTranslationRequest req) {

        Example example = exampleRepository.findByIdOptional(req.exampleId())
                .orElseThrow(BadRequestException::dataNotFound);

        Language language = languageRepository.findByCode(req.lang().toUpperCase())
                .orElseThrow(BadRequestException::dataNotFound);

        ExampleTranslation exampleTranslation = new ExampleTranslation();
        exampleTranslation.setExample(example);
        exampleTranslation.setLanguage(language);
        exampleTranslation.setText(req.translation());

        exampleTranslationRepository.persist(exampleTranslation);

        return exampleTranslation;
    }


    @Override
    @Transactional
    public Example updateExample(Long exampleId, String newText) {
        Example example = exampleRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::dataNotFound);

        example.setText(newText);
        return example;
    }

    @Override
    @Transactional
    public ExampleTranslation updateExampleTranslation(Long exampleTranslationId, String newText) {
        ExampleTranslation et = exampleTranslationRepository
                .findByIdOptional(exampleTranslationId)
                .orElseThrow(BadRequestException::dataNotFound);

        et.setText(newText);
        return et;
    }

    @Override
    @Transactional
    public void deleteExample(Long exampleId) {
        Example example = exampleRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::dataNotFound);
        exampleRepository.delete(example);
    }

    @Override
    @Transactional
    public void deleteExampleTranslation(Long exampleTranslationId) {
        ExampleTranslation exampleTranslation = exampleTranslationRepository.findByIdOptional(exampleTranslationId)
                .orElseThrow(BadRequestException::dataNotFound);
        exampleTranslationRepository.delete(exampleTranslation);
    }


    private Level parhseLevel(int level) {
        return levelRepository.findByLevel(level)
                .orElseThrow(BadRequestException::wordNotFound);
    }

    private PosType parsePosType(String posType) {
        if (posType == null || posType.isBlank()) {
            return null;
        }

        try {
            return PosType.valueOf(posType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw BadRequestException.invalidPosType();
        }
    }
}
