package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.PrioritisedEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Level extends PrioritisedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String levelName;

    @OneToMany(mappedBy = "level")
    private List<Challenge> challenges = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

}
