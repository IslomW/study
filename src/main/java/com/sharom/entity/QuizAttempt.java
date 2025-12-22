package com.sharom.entity;


import jakarta.persistence.*;

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

    @Column(nullable = false)
    private boolean completed;

}
