package com.sharom.entity;//package com.sharom.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//public class ThematicQuiz extends AuditEntity{
//
//    private String title;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "topik_type", nullable = false)
//    private TopikType topikType;
//
//    private int level;
//
//    private Long relatedUnitId;
//
//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<ThematicQuizQuestion> questions = new ArrayList<>();
//
//}
