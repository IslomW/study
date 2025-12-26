package com.sharom.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "example_translations")
public class ExampleTranslation extends BaseEntity {

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @ManyToOne
    @JoinColumn(name = "example_id", nullable = false)
    private Example example;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }
}
