package com.sharom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "topic_level")
public class Level extends BaseEntity{

    @Column(nullable = false, unique = true)
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(name = "topik_type", nullable = false)
    private TopikType topikType;

    @Column(length = 500)
    private String description;

    private Integer minScore;
}
