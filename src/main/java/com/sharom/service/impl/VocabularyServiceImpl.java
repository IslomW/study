package com.sharom.service.impl;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.Level;
import com.sharom.entity.PosType;
import com.sharom.entity.VocabularyWord;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.ExamplePairRepository;
import com.sharom.repository.VocabularyRepository;
import com.sharom.service.VocabularyService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@ApplicationScoped
public class VocabularyServiceImpl implements VocabularyService {

    private final VocabularyRepository vocabularyRepository;
    private final ExamplePairRepository examplePairRepository;


    public VocabularyServiceImpl(VocabularyRepository vocabularyRepository, ExamplePairRepository examplePairRepository) {
        this.vocabularyRepository = vocabularyRepository;
        this.examplePairRepository = examplePairRepository;
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
    public List<VocabularyWord> getAllWordsFiltered(Level level, PosType posType,int page, int size, String sortBy) {
        return vocabularyRepository.findFiltered(
                level, posType, page, size, sortBy
        );
    }

    @Override
    public VocabularyWord getRandomWordByLevel(Level level) {
        long total = vocabularyRepository.countByLevel(level);

        if (total == 0) {
            return null;
        }

        int randomIndex = ThreadLocalRandom.current()
                .nextInt((int) total);

        return vocabularyRepository
                .findRandomByLevel(level, randomIndex);
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
}
