package com.sharom.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "example")
public class Example extends AuditEntity {

    // пример на языке слова
    @Column(nullable = false, length = 500)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id", nullable = false)
    private Word word;

    @OneToMany(
            mappedBy = "example",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExampleTranslation> translations = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public List<ExampleTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<ExampleTranslation> translations) {
        this.translations = translations;
    }
}
