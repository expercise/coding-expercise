package com.expercise.domain.configuration;

import com.expercise.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "ID_GENERATOR", sequenceName = "SEQ_CONFIGURATION")
public class Configuration extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, length = 512)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
