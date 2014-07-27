package com.ufukuzun.kodility.domain.challenge;

import com.ufukuzun.kodility.domain.PrioritizedEntity;
import com.ufukuzun.kodility.enums.Lingo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Challenge> getChallenges() {
        return challenges;
    }

    public void setChallenges(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public List<Challenge> getApprovedChallenges() {
        List<Challenge> approvedChallenges = new ArrayList<>(challenges);
        CollectionUtils.filter(approvedChallenges, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((Challenge) object).isApproved();
            }
        });
        return approvedChallenges;
    }

}
