package com.sharom.entity;//package com.sharom.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.util.List;
//
//
//@Entity
//@Getter
//@Setter
//@Table(name = "mock_questions")
//@NoArgsConstructor
//public class MockQuestion {
//
//    private Integer questionNumber;
//
//    private String question;
//
//    private Integer correctAnswerIndex;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mock_section_id")
//    private MockSection mockSection;
//
//    // Для вопросов аудирования
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "listening_track_id")
//    private ListeningTrack audioTrack;
//
//    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy("optionIndex ASC")
//    private List<QuestionOption> options;
//
//}
