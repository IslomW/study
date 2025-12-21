package com.sharom.entity;

import jakarta.persistence.*;

@Entity
public class ExamplePair extends AuditEntity {
    @Column(name = "sentence", length = 500)
    private String sentence;

    @Column(name = "translation", length = 500)
    private String translation;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private VocabularyWord vocabularyWord;

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public VocabularyWord getVocabularyWord() {
        return vocabularyWord;
    }

    public void setVocabularyWord(VocabularyWord vocabularyWord) {
        this.vocabularyWord = vocabularyWord;
    }


    // Геттеры, сеттеры и конструкторы
}
