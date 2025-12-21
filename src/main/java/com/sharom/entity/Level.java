package com.sharom.entity;

import com.sharom.enums.TopikType;
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


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public TopikType getTopikType() {
        return topikType;
    }

    public void setTopikType(TopikType topikType) {
        this.topikType = topikType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinScore() {
        return minScore;
    }

    public void setMinScore(Integer minScore) {
        this.minScore = minScore;
    }
}
