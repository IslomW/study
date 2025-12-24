package com.sharom.entity;//package com.sharom.entity;


import com.sharom.enums.PosType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vocabulary_words")
public class VocabularyWord extends AuditEntity {

    @Column(nullable = false)
    private String word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "pos_type", nullable = false)
    private PosType posType;

    @OneToMany(mappedBy = "vocabularyWord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VocabularyWordTranslation> translations = new ArrayList<>();


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
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

    public List<VocabularyWordTranslation> getTranslations() {
        return translations;
    }

    public void addTranslation(VocabularyWordTranslation tr) {
        tr.setVocabularyWord(this);
        translations.add(tr);
    }

    public void removeTranslation(VocabularyWordTranslation tr) {
        translations.remove(tr);
        tr.setVocabularyWord(null);
    }
}

