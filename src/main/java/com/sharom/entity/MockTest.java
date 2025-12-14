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
//@Table(name = "mock_tests")
//@Getter
//@Setter
//@NoArgsConstructor
//public class MockTest extends AuditEntity{
//
//    private String title;
//
//    @Enumerated(EnumType.STRING)
//    private TopikType topikType;
//
//    private Integer maxScore;
//
//    private Integer durationMinutes;
//
//    @OneToMany(mappedBy = "mockTest", cascade = CascadeType.ALL, orphanRemoval = true)
//    public List<MockSection> sections;
//}
