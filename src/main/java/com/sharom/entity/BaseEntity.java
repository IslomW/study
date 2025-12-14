package com.sharom.entity;

import com.sharom.utils.TsidGen;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass

public class BaseEntity {


    @Id
    @TsidGen
    protected Long id;


    public Long getId() {
        return id;
    }

}
