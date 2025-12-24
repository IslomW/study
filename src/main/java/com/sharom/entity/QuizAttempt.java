package com.sharom.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_attempts")
public class QuizAttempt extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    private int score;

    private int totalQuestions;

    private int correctAnswers;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;


    @Column(nullable = false)
    private boolean completed;

}
