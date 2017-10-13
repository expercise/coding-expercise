package com.expercise.repository.level;

import com.expercise.domain.level.Level;
import com.expercise.repository.BaseRepository;

import java.util.List;

public interface LevelRepository extends BaseRepository<Level> {

    Level findByPriority(Integer priority);

    List<Level> findAllByOrderByPriority();

}
