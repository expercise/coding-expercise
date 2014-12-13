package com.kodility.domain.level;

import com.kodility.domain.PrioritizedEntity;
import com.kodility.domain.challenge.Challenge;
import com.kodility.domain.theme.Theme;
import com.kodility.enums.Lingo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Level extends PrioritizedEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
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

    private String getNameFor(Lingo lingo) {
        return names.get(lingo);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public List<Challenge> getApprovedChallenges() {
        return challenges.stream()
                .filter(c -> c.isApproved())
                .collect(Collectors.toList());
    }

}
