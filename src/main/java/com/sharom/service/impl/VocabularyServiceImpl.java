package com.sharom.service.impl;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.Level;
import com.sharom.entity.VocabularyWord;
import com.sharom.entity.VocabularyWordTranslation;
import com.sharom.enums.PosType;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.ExamplePairRepository;
import com.sharom.repository.LevelRepository;
import com.sharom.repository.VocabularyRepository;
import com.sharom.repository.VocabularyWordTranslationRepository;
import com.sharom.service.VocabularyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class VocabularyServiceImpl implements VocabularyService {

    @Inject
    VocabularyRepository vocabularyRepository;
    @Inject
    VocabularyWordTranslationRepository translationRepository;
    @Inject
    ExamplePairRepository exampleRepository;
    @Inject
    LevelRepository levelRepository;

    @Override
    @Transactional
    public VocabularyWord createWord(String word, PosType posType, Long levelId) {
        Level level = levelRepository.findByIdOptional(levelId)
                .orElseThrow(BadRequestException::levelNotFound);

        VocabularyWord vw = new VocabularyWord();
        vw.setWord(word);
        vw.setPosType(posType);
        vw.setLevel(level);

        vocabularyRepository.persist(vw);
        return vw;
    }

    @Override
    @Transactional
    public VocabularyWordTranslation addTranslation(Long wordId, String lang, String translationText) {
        VocabularyWord word = vocabularyRepository.findByIdOptional(wordId)
                .orElseThrow(BadRequestException::vocabularyNotFound);

        VocabularyWordTranslation translation = new VocabularyWordTranslation();
        translation.setLang(lang);
        translation.setTranslation(translationText);
        word.addTranslation(translation);

        return translation;
    }

    @Override
    @Transactional
    public ExamplePair addExample(Long translationId, String exampleText) {
        VocabularyWordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);

        ExamplePair example = new ExamplePair();
        example.setText(exampleText);
        translation.addExample(example);

        return example;
    }

    @Override
    @Transactional
    public VocabularyWordTranslation updateTranslation(Long translationId, String newText) {
        VocabularyWordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);

        translation.setTranslation(newText);
        return translation;
    }

    @Override
    @Transactional
    public ExamplePair updateExample(Long exampleId, String newText) {
        ExamplePair example = exampleRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::dataNotFound);

        example.setText(newText);
        return example;
    }

    @Override
    @Transactional
    public void deleteWord(Long wordId) {
        VocabularyWord word = vocabularyRepository.findByIdOptional(wordId)
                .orElseThrow(BadRequestException::vocabularyNotFound);
        vocabularyRepository.delete(word);
    }

    @Override
    @Transactional
    public void deleteTranslation(Long translationId) {
        VocabularyWordTranslation translation = translationRepository.findByIdOptional(translationId)
                .orElseThrow(BadRequestException::dataNotFound);
        translationRepository.delete(translation);
    }

    @Override
    @Transactional
    public void deleteExample(Long exampleId) {
        ExamplePair example = exampleRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::dataNotFound);
        exampleRepository.delete(example);
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
