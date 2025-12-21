package com.sharom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class GrammarRuleExample extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private GrammarRuleTranslation translation;

    @Column(length = 2000)
    private String text;


    public GrammarRuleTranslation getTranslation() {
        return translation;
    }

    public void setTranslation(GrammarRuleTranslation translation) {
        this.translation = translation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
