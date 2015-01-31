package com.expercise.dao.level;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.level.Level;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LevelDao extends AbstractHibernateDao<Level> {

    protected LevelDao() {
        super(Level.class);
    }

    public Level findOneBy(Integer priority) {
        return (Level) getCriteria().add(Restrictions.eq("priority", priority)).uniqueResult();
    }

}
