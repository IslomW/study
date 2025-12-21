package com.sharom.entity;//package com.sharom.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vocabulary_words")
public class VocabularyWord extends AuditEntity {

    @Column(nullable = false)
    private String word;

    @Column(name = "translation", nullable = false)
    private String translation;

    @OneToMany(mappedBy = "vocabularyWord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExamplePair> examples = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    public Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "pos_type", nullable = false)
    private PosType posType;


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public void setExamples(List<ExamplePair> examples) {
        this.examples = examples;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public PosType getPosType() {
        return posType;
    }

    public void setPosType(PosType posType) {
        this.posType = posType;
    }
}

