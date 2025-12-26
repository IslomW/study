package com.sharom.service.impl;

import com.sharom.dto.CreateExampleRequest;
import com.sharom.dto.CreateExampleTranslationRequest;
import com.sharom.dto.CreateTranslationRequest;
import com.sharom.dto.CreateWordRequest;
import com.sharom.entity.*;
import com.sharom.enums.PosType;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.*;
import com.sharom.service.VocabularyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class VocabularyServiceImpl implements VocabularyService {

    @Inject
    WordRepository wordRepository;
    @Inject
    WordTranslationRepository translationRepository;
    @Inject
    ExampleRepository exampleRepository;
    @Inject
    ExampleTranslationRepository exampleTranslationRepository;
    @Inject
    LevelRepository levelRepository;
    @Inject
    LanguageRepository languageRepository;

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
    public WordTranslation updateTranslation(Long translationId, String newText) {
        WordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);

//        translation.setTranslation(newText);
        return translation;
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
    public void deleteExample(Long exampleId) {
        Example example = exampleRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::dataNotFound);
        exampleRepository.delete(example);
    }

    @Override
    @Transactional
    public void deleteExampleTranslation(Long exampleTranslationId){
        ExampleTranslation exampleTranslation = exampleTranslationRepository.findByIdOptional(exampleTranslationId)
                .orElseThrow(BadRequestException::dataNotFound);
        exampleTranslationRepository.delete(exampleTranslation);
    }


    //    private Level parhseLevel(int level){
//        return levelRepository.findByLevel(level)
//                .orElseThrow(BadRequestException::vocabularyNotFound);
//    }

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
