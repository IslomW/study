package com.sharom.service.impl;

import com.sharom.entity.*;
import com.sharom.exception.BadRequestException;
import com.sharom.repository.ExampleRepository;
import com.sharom.repository.UserVocabularyProgressRepository;
import com.sharom.repository.WordRepository;
import com.sharom.repository.WordTranslationRepository;
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
    ExampleRepository exampleRepository;


    @Inject
    WordRepository wordRepository;

    @Inject
    WordTranslationRepository vocabularyTranslationRepository;

    @Inject
    I18nService i18nService;

    @Inject
    UserVocabularyProgressRepository progressRepository;


    @Inject
    UserContextService userContextService;


    public Quiz generateRandomQuiz(int numQuestions) {

        Long userId = userContextService.getUserId();
        String lang = userContextService.getLanguage();

        Quiz quiz = new Quiz();

        quiz.setTitle(i18nService.getMessage("quiz.title.fill_in_blank", lang));

        List<Word> words = wordRepository.findLearnedWords(userId);
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
    public QuizQuestion generateFillInTheBlankQuestion(Word word) {

        Example example = exampleRepository.findRandomByStudentLearnedWord(word.getId())
                .orElseThrow(BadRequestException::examplePariNotFound);

        String text = example.getText();
        String questionText = text.replaceFirst(
                "\\b" + Pattern.quote(word.getWord()) + "\\b", "____"
        );

        String correctAnswer = word.getWord();

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        List<String> distractors = wordRepository
                .findRandomWordsExcluding(word.getId(), 3);

        options.addAll(distractors);

        Collections.shuffle(options);


        QuizQuestion question = new QuizQuestion();
        question.setQuestionText(questionText);
        question.setOptions(options);
        question.setCorrectAnswerIndex(options.indexOf(correctAnswer));

        return question;
    }




    private void generateVocabularyQuestionsFromProgress(
            Quiz quiz,
            User user,
            int count
    ) {

        List<Word> learnedWords =
                progressRepository.findLearnedWords(user);

        if (learnedWords.isEmpty()) {
            throw new IllegalStateException("User has no learned words");
        }

        Collections.shuffle(learnedWords);

        int limit = Math.min(count, learnedWords.size());

        for (int i = 0; i < limit; i++) {

            Word word = learnedWords.get(i);

            QuizQuestion q = new QuizQuestion();
            q.setQuiz(quiz);
            q.setQuestionText("Translate: " + word.getWord());

            List<String> options = generateOptions(word);
            q.setOptions(options);
            q.setCorrectAnswerIndex(
                    options.indexOf(getCorrectTranslation(word, userContextService.getLanguage()))
            );

            quiz.getQuestions().add(q);
        }
    }

    private List<String> generateOptions(Word word) {

        String correct = getCorrectTranslation(word, userContextService.getLanguage());

        List<String> options = new ArrayList<>();
        options.add(correct);

        List<String> randomTranslations =
                wordRepository.findRandomTranslationsExcluding(word.getId(), userContextService.getLanguage(), 3);

        options.addAll(randomTranslations.stream()
                .filter(t -> !t.equals(correct))
                .toList());

        Collections.shuffle(options);
        return options;
    }


    private String getCorrectTranslation(Word word, String lang) {
        return vocabularyTranslationRepository
                .findTranslation(word.getId(), lang)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No translation for word " + word.getId() + " and lang " + lang
                        )
                );
    }





}
