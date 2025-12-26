package com.sharom.service;

import com.sharom.entity.QuizQuestion;
import com.sharom.entity.Word;

public interface QuizService {
    QuizQuestion generateFillInTheBlankQuestion(Word word);

}
