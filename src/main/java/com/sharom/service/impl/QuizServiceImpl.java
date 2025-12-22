package com.sharom.service.impl;

import com.sharom.entity.ExamplePair;
import com.sharom.entity.Quiz;
import com.sharom.entity.QuizQuestion;
import com.sharom.entity.VocabularyWord;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.ExamplePairRepository;
import com.sharom.repository.VocabularyRepository;
import com.sharom.service.QuizService;
import com.sharom.utils.UserContextService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class QuizServiceImpl implements QuizService {

    @Inject
    ExamplePairRepository exampleRepository;


    @Inject
    VocabularyRepository vocabularyRepository;

    @Inject
    I18nService i18nService;

    @Inject
    UserContextService userContextService;


    public Quiz generateRandomQuiz(int numQuestions) {

        Long userId = userContextService.getUserId();
        String lang = userContextService.getLanguage();

        Quiz quiz = new Quiz();

        quiz.setTitle(i18nService.getMessage("quiz.title.fill_in_blank", lang));

        List<VocabularyWord> words = vocabularyRepository.findLearnedWords(userId);
        if (words.isEmpty()) {
            throw BadRequestException.vocabularyNotFound();
        }
        Collections.shuffle(words);

        List<QuizQuestion> questions = new ArrayList<>();
        for (int i = 0; i < Math.min(numQuestions, words.size()); i++) {
            QuizQuestion q = generateFillInTheBlankQuestion(words.get(i));
            questions.add(q);
        }

        quiz.setQuestions(questions);
        return quiz;
    }

    @Override
    public QuizQuestion generateFillInTheBlankQuestion(VocabularyWord word) {

        ExamplePair example = exampleRepository.findRandomByStudentLearnedWord(word.getId())
                .orElseThrow(BadRequestException::examplePariNotFound);

        String text = example.getText();
        String questionText = text.replaceFirst(
                "\\b" + Pattern.quote(word.getWord()) + "\\b", "____"
        );

        String correctAnswer = word.getWord();

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        List<String> distractors = vocabularyRepository
                .findRandomWordsExcluding(word.getId(), 3);

        options.addAll(distractors);

        Collections.shuffle(options);


        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(questionText);
        question.setOptions(options);
        question.setCorrectAnswerIndex(options.indexOf(correctAnswer));

        return question;
    }
}
