package com.sharom.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user_learned_words")
public class UserLearnedWord extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_word_id")
    private VocabularyWord word;

    private boolean learned; // true если слово изучено

}
