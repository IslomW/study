package com.sharom.service;

import com.sharom.entity.QuizQuestion;
import com.sharom.entity.VocabularyWord;

public interface QuizService {
    QuizQuestion generateFillInTheBlankQuestion(VocabularyWord word);

}
