package com.expercise.controller.level.model;

import com.expercise.domain.level.Level;
import com.expercise.enums.Lingo;
import com.expercise.utils.validation.UniqueLevelPriority;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@UniqueLevelPriority
public class SaveLevelRequest {

    private Long levelId;

    @NotEmpty
    private String turkishName;

    @NotEmpty
    private String englishName;

    @Range(min = 1, max = 100)
    @NotNull
    private Integer priority;

    public Level toLevel() {
        Level level = new Level();

        level.setId(levelId);

        Map<Lingo, String> names = new HashMap<>();
        names.put(Lingo.Turkish, turkishName);
        names.put(Lingo.English, englishName);
        level.setNames(names);

        level.setPriority(priority);

        return level;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getTurkishName() {
        return turkishName;
    }

    public void setTurkishName(String turkishName) {
        this.turkishName = turkishName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
