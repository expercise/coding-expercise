package com.expercise.domain.theme;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.domain.level.Level;
import com.expercise.enums.Lingo;
import com.expercise.service.util.PrioritySorter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Theme extends PrioritizedEntity {

    public static final String THEME_ID_FOR_NOT_THEMED_CHALLENGES = "others";

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Name", nullable = false)
    private Map<Lingo, String> names = new HashMap<>();

    @OneToMany(mappedBy = "theme")
    private List<Level> levels = new ArrayList<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getIdAsString() {
        return id != null ? id.toString() : THEME_ID_FOR_NOT_THEMED_CHALLENGES;
    }

    public Map<Lingo, String> getNames() {
        return names;
    }

    public void setNames(Map<Lingo, String> names) {
        this.names = names;
    }

    public String getNameFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        return names.get(lingo);
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    public List<Level> getOrderedLevels() {
        return levels.stream()
                .sorted(new PrioritySorter())
                .collect(Collectors.toList());
    }

}
