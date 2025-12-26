package com.sharom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "example_pairs")
public class ExamplePair extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translation_id", nullable = false)
    private VocabularyWordTranslation translation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_word_id")
    private VocabularyWord vocabularyWord;


    @Column(name = "sentence", length = 500)
    private String sentence;

    @Column(length = 2000, nullable = false)
    private String text;


    public VocabularyWordTranslation getTranslation() {
        return translation;
    }

    public void setTranslation(VocabularyWordTranslation translation) {
        this.translation = translation;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
