package com.kodility.dao.challenge;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.challenge.Level;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LevelDao extends AbstractHibernateDao<Level> {

    protected LevelDao() {
        super(Level.class);
    }

    public List<Level> findAllOrdered() {
        return getCriteria().addOrder(Order.asc("priority")).list();
    }

    public Level findOneBy(Integer priority) {
        return (Level) getCriteria().add(Restrictions.eq("priority", priority)).uniqueResult();
    }

}
