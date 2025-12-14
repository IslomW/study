package com.sharom.entity;//package com.sharom.entity;
//
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "vocabulary_words")
//public class VocabularyWord extends AuditEntity {
//
//    @Column(nullable = false)
//    private String word;
//
//    @Column(name = "translation", nullable = false)
//    private String translation;
//
//    @Column(name = "example_sentence", length = 500)
//    private String exampleSentence;
//
//    @Column(name = "translation_example", length = 500)
//    public String translationExample;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "level_id", nullable = false)
//    public Level level;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "pos_type", nullable = false)
//    private PosType posType;
//
//}
