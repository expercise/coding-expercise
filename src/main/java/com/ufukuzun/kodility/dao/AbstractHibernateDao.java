package com.ufukuzun.kodility.dao;

import com.ufukuzun.kodility.domain.AbstractEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractHibernateDao<T extends AbstractEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class klass;

    protected AbstractHibernateDao(Class klass) {
        this.klass = klass;
    }

    public T findOne(final long id) {
        return (T) getCurrentSession().get(klass, id);
    }

    public T findOneBy(String propertyName, Object value) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(propertyName, value));
        return (T) criteria.uniqueResult();
    }

    public List<T> findAll() {
        return getCriteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Transactional
    public void save(final T entity) {
        getCurrentSession().persist(entity);
    }

    @Transactional
    public T update(final T entity) {
        return (T) getCurrentSession().merge(entity);
    }

    @Transactional
    public void delete(final T entity) {
        getCurrentSession().delete(entity);
    }

    protected Criteria getCriteria() {
        return getCurrentSession().createCriteria(klass);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
