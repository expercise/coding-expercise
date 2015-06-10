package com.expercise.domain.theme;

import com.expercise.domain.PrioritizedEntity;
import com.expercise.domain.level.Level;
import com.expercise.enums.Lingo;
import com.expercise.service.util.PrioritySorter;
import com.expercise.utils.UrlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Theme extends PrioritizedEntity {

    public static final String URL_FOR_NOT_THEMED_CHALLENGES = "/themes/other-challenges";

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

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "Lingo", nullable = false)
    @Column(name = "Description", nullable = false)
    private Map<Lingo, String> descriptions = new HashMap<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public long getTitleColor() {
        int mod = 4;
        return isPersisted() ? id % mod : mod;
    }

    public String getBookmarkableUrl() {
        if (id != null) {
            return "/themes/" + id + "/" + getBookmarkableName();
        }
        return URL_FOR_NOT_THEMED_CHALLENGES;
    }

    public boolean isBookmarkableNameChanged(String bookmarkableThemeName) {
        return !StringUtils.equals(getBookmarkableName(), bookmarkableThemeName);
    }

    private String getBookmarkableName() {
        String lingoShortName = LocaleContextHolder.getLocale().toString();
        return UrlUtils.makeBookmarkable(getNameFor(lingoShortName));
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

    public Map<Lingo, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<Lingo, String> descriptions) {
        this.descriptions = descriptions;
    }

    public String getDescription() {
        String description = Lingo.getValueFrom(descriptions);
        if (StringUtils.isBlank(description)) {
            return StringUtils.EMPTY;
        }
        return description;
    }

    public List<Level> getOrderedLevels() {
        return levels.stream()
                .sorted(new PrioritySorter())
                .collect(Collectors.toList());
    }

}
