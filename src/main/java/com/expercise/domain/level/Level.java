package com.expercise.domain.level;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.theme.Theme;
import com.expercise.enums.Lingo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Level extends PrioritizedEntity {

    private static final long serialVersionUID = -1863753168513276995L;

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo")
    @Column(name = "Name", nullable = false)
    private Map<Lingo, String> names = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Theme theme;

    @OneToMany(mappedBy = "level")
    private List<Challenge> challenges = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Lingo, String> getNames() {
        return names;
    }

    public void setNames(Map<Lingo, String> names) {
        this.names = names;
    }

    public String getTurkishName() {
        return getNameFor(Lingo.Turkish);
    }

    public String getEnglishName() {
        return getNameFor(Lingo.English);
    }

    public String getNameFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        return getNameFor(lingo);
    }

    public String getNameForChallengeManagement(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName).get();
        String levelName = getNameFor(lingo);
        if (theme != null) {
            levelName = theme.getNameFor(lingoShortName) + " - " + levelName;
        }
        return levelName;
    }

    private String getNameFor(Lingo lingo) {
        return names.get(lingo);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public boolean hasTheme() {
        return theme != null;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public List<Challenge> getApprovedChallenges() {
        return challenges.stream()
                .filter(Challenge::isApproved)
                .collect(Collectors.toList());
    }

}
