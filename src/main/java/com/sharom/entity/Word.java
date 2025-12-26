package com.sharom.entity;//package com.sharom.entity;


import com.sharom.enums.PosType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vocabulary_words")
public class Word extends AuditEntity {

    @Column(nullable = false)
    private String text;

    // язык слова
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;


    @Enumerated(EnumType.STRING)
    @Column(name = "pos_type", nullable = false)
    private PosType posType;

    // переводы слова
    @OneToMany(
            mappedBy = "word",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<WordTranslation> translations = new ArrayList<>();

    // Примеры, относящиеся к исходному слову
    @OneToMany(mappedBy = "Word", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Example> examples = new ArrayList<>();

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

    public PosType getPosType() {
        return posType;
    }

    public void setPosType(PosType posType) {
        this.posType = posType;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public List<WordTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<WordTranslation> translations) {
        this.translations = translations;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public void addTranslation(WordTranslation translation) {
        translations.add(translation);
        translation.setWord(this);
    }
}

