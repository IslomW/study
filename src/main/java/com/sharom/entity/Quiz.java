package com.sharom.entity;//package com.sharom.entity;

import com.sharom.enums.QuizTopicType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

public class Quiz extends AuditEntity{

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private QuizTopicType quizTopicType;

    private int level;

    private Long relatedUnitId;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> questions = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuizTopicType getQuizTopicType() {
        return quizTopicType;
    }

    public void setQuizTopicType(QuizTopicType quizTopicType) {
        this.quizTopicType = quizTopicType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getRelatedUnitId() {
        return relatedUnitId;
    }

    public void setRelatedUnitId(Long relatedUnitId) {
        this.relatedUnitId = relatedUnitId;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }
}
