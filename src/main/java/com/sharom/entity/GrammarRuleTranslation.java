package com.sharom.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class GrammarRuleTranslation extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grammar_rule_id", nullable = false)
    private GrammarRule grammarRule;

    @Column(nullable = false, length = 10)
    private String languageCode; // например "en", "ru", "uz"

    @Column(nullable = false, length = 1000)
    private String description;

    @OneToMany(mappedBy = "grammarRuleTranslation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrammarRuleExample> examples = new ArrayList<>();


    public GrammarRule getGrammarRule() {
        return grammarRule;
    }

    public void setGrammarRule(GrammarRule grammarRule) {
        this.grammarRule = grammarRule;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GrammarRuleExample> getExamples() {
        return examples;
    }

    public void setExamples(List<GrammarRuleExample> examples) {
        this.examples = examples;
    }
}
