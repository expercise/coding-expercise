package com.expercise.repository.theme;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.theme.Theme;
import org.springframework.stereotype.Repository;

@Repository
public class ThemeRepository extends BaseRepository<Theme> {

    protected ThemeRepository() {
        super(Theme.class);
    }

}
