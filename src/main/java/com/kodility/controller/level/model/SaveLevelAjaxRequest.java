package com.kodility.controller.level.model;

import com.kodility.domain.challenge.Level;
import com.kodility.enums.Lingo;
import com.kodility.utils.validation.UniqueLevelPriority;
import com.ufukuzun.myth.dialect.model.AjaxRequest;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

public class SaveLevelAjaxRequest extends AjaxRequest<SaveLevelAjaxRequest.LevelModel> {

    public static class LevelModel {

        @NotEmpty
        private String turkishName;

        @NotEmpty
        private String englishName;

        @Range(min = 1, max = 100)
        @NotNull
        @UniqueLevelPriority
        private Integer priority;

        public Level toLevel() {
            Level level = new Level();

            Map<Lingo, String> names = new HashMap<>();
            names.put(Lingo.Turkish, turkishName);
            names.put(Lingo.English, englishName);
            level.setNames(names);

            level.setPriority(priority);

            return level;
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

}
