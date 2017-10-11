package com.expercise.repository.level;

import com.expercise.repository.BaseRepository;
import com.expercise.domain.level.Level;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LevelRepository extends BaseRepository<Level> {

    protected LevelRepository() {
        super(Level.class);
    }

    public Level findOneBy(Integer priority) {
        return (Level) getCriteria().add(Restrictions.eq("priority", priority)).uniqueResult();
    }

}
