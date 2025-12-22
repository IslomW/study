package com.sharom.entity;

import jakarta.persistence.*;

@Entity
public class GrammarRuleExample extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "translation_id", nullable = false)
    private GrammarRuleTranslation grammarRuleTranslation;

    @Column(length = 2000)
    private String text;


    public GrammarRuleTranslation getTranslation() {
        return grammarRuleTranslation;
    }

    public void setTranslation(GrammarRuleTranslation grammarRuleTranslation) {
        this.grammarRuleTranslation = grammarRuleTranslation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
