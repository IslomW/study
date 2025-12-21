package com.sharom.service.impl;

import com.sharom.entity.*;
import com.sharom.enums.PosType;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.ExamplePairRepository;
import com.sharom.repository.LevelRepository;
import com.sharom.repository.VocabularyRepository;
import com.sharom.service.VocabularyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class VocabularyServiceImpl implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final ExamplePairRepository examplePairRepository;
    private final LevelRepository levelRepository;


    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository, ExamplePairRepository examplePairRepository, LevelRepository levelRepository) {
        this.vocabularyRepository = vocabularyRepository;
        this.examplePairRepository = examplePairRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public VocabularyWord addWord(VocabularyWord word) {
        vocabularyRepository.persist(word);
        return word;
    }

    @Override
    @Transactional
    public VocabularyWord addSentenceById(Long id, List<ExamplePair> sentences) {
        VocabularyWord word = vocabularyRepository.findByIdOptional(id)
                .orElseThrow(BadRequestException::vocabularyNotFound);


        for (ExamplePair sentence : sentences) {
            sentence.setVocabularyWord(word);
            word.getExamples().add(sentence);
        }


        return word;
    }

    @Override
    public VocabularyWord updateWord(Long id, VocabularyWord word) {

        VocabularyWord existing = vocabularyRepository.findById(id);
        if (existing == null) {
            throw BadRequestException.userNotFound();
        }

        existing.setWord(word.getWord());
        existing.setLevel(word.getLevel());
        existing.setPosType(word.getPosType());

        return existing;
    }


    @Override
    public VocabularyWord getWordById(Long id) {
        return vocabularyRepository.findByIdOptional(id)
                .orElseThrow(BadRequestException::vocabularyNotFound);
    }

    @Override
    public List<VocabularyWord> getAllWordsFiltered(int level, String posType,int page, int size, String sortBy) {
        return vocabularyRepository.findFiltered(
                parhseLevel(level), parsePosType(posType), page, size, sortBy
        );
    }

    @Override
    public VocabularyWord getRandomWordByLevel(int level) {
        long total = vocabularyRepository.countByLevel(parhseLevel(level));

        if (total == 0) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current()
                .nextInt((int) total);

        return vocabularyRepository
                .findRandomByLevel(parhseLevel(level), randomIndex);
    }

    @Override
    @Transactional
    public void deleteExampleSentence(Long exampleId) {
        ExamplePair example = examplePairRepository.findByIdOptional(exampleId)
                .orElseThrow(BadRequestException::vocabularyNotFound);

        examplePairRepository.deleteById(exampleId);
    }

    @Override
    public void deleteWord(Long wordId) {
        vocabularyRepository.deleteById(wordId);
    }

    private Level parhseLevel(int level){
        return levelRepository.findByLevel(level)
                .orElseThrow(BadRequestException::vocabularyNotFound);
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
