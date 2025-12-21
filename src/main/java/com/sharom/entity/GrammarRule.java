package com.sharom.entity;//package com.sharom.entity;
//
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "grammar_rules")
public class GrammarRule extends AuditEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id", nullable = false)
    public Level level;

    @Column(nullable = false, unique = true)
    private String rule;

    @OneToMany(mappedBy = "grammarRule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrammarRuleTranslation> translations = new ArrayList<>();


    public GrammarRule(Level level, String rule, List<GrammarRuleTranslation> translations) {
        this.level = level;
        this.rule = rule;
        this.translations = translations;
    }

    public GrammarRule() {

    }
}
