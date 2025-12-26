package com.sharom.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vocabulary_word_translations",
        uniqueConstraints = @UniqueConstraint(
                name = "unique_translation_idx",
                columnNames = {"vocabulary_word_id", "lang"}
        )
)
public class VocabularyWordTranslation extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vocabulary_word_id", nullable = false)
    private VocabularyWord vocabularyWord;

    @Column(length = 5, nullable = false)
    private String lang; // "en", "ru", "uz"

    @Column(nullable = false)
    private String translation;

    @OneToMany(mappedBy = "translation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamplePair> examples = new ArrayList<>();

    public VocabularyWord getVocabularyWord() {
        return vocabularyWord;
    }

    public void setVocabularyWord(VocabularyWord vocabularyWord) {
        this.vocabularyWord = vocabularyWord;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public List<ExamplePair> getExamples() {
        return examples;
    }


    public void addExample(ExamplePair example) {
        example.setTranslation(this);
        examples.add(example);
    }

    public void removeExample(ExamplePair example) {
        examples.remove(example);
        example.setTranslation(null);
    }


}
