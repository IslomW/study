package com.sharom.entity;//package com.sharom.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//@Entity
//@Table(name = "mock_sections")
//@Getter
//@Setter
//@NoArgsConstructor
//public class MockSection extends AuditEntity {
//    // Использование Enum для типа секции
//    @Enumerated(EnumType.STRING)
//    @Column(name = "section_type") // Переименовано для ясности
//    public SectionType sectionType;
//
//    public Integer orderInTest;
//    public Integer questionStartNumber;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mock_test_id")
//    public MockTest mockTest;
//
//    @OneToMany(mappedBy = "mockSection", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("questionNumber ASC")
//    public List<MockQuestion> questions;
//}
