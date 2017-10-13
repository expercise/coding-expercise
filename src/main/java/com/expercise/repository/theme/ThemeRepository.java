package com.expercise.repository.theme;

import com.expercise.domain.theme.Theme;
import com.expercise.repository.BaseRepository;

import java.util.List;

public interface ThemeRepository extends BaseRepository<Theme> {

    List<Theme> findAllByOrderByPriority();

}
