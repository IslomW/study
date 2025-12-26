package com.sharom.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "languages")
public class Language extends BaseEntity{

    // EN, RU, UZ, KO
    @Column(nullable = false, unique = true, length = 5)
    private String code;

    // English, Русский, O‘zbek, 한국어
    @Column(nullable = false)
    private String name;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
