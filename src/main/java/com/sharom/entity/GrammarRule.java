package com.sharom.entity;//package com.sharom.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Entity
//@Getter
//@Setter
//@Table(name = "grammar_rules")
//@NoArgsConstructor
//public class GrammarRule extends AuditEntity {
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "level_id", nullable = false)
//    public Level level;
//
//    @Column(nullable = false, unique = true)
//    private String rule;
//
//    @Column(length = 1000)
//    private String description;
//
//    @Column(length = 5000)
//    private String example;
//
//}
