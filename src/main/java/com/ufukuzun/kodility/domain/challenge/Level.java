package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.PrioritizedEntity;
import com.ufukuzun.kodility.enums.Lingo;

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

    public String getNameFor(String lingoShortName) {
        Lingo lingo = Lingo.getLingo(lingoShortName);
        return names.get(lingo);
    }

    public String getTurkishName() {
        return names.get(Lingo.Turkish);
    }

    public String getEnglishName() {
        return names.get(Lingo.English);
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
