package com.sharom.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "word_translations",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_translation_idx",
                columnNames = {"vocabulary_word_id", "lang"}
        )
)
public class WordTranslation extends BaseEntity {

    // перевод слова
    @Column(nullable = false)
    private String text;

    // язык перевода
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_word_id", nullable = false)
    private Word word;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
