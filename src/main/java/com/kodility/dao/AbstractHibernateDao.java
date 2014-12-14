package com.kodility.dao;

import com.kodility.domain.AbstractEntity;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public abstract class AbstractHibernateDao<T extends AbstractEntity> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class klass;

    protected AbstractHibernateDao(Class klass) {
        this.klass = klass;
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

    public T findOne(final long id) {
        return (T) getCurrentSession().get(klass, id);
    }

    public T findOneBy(String propertyName, Object value) {
        Criteria criteria = getCriteria();
        criteria.add(Restrictions.eq(propertyName, value));
        return (T) criteria.uniqueResult();
    }

    protected T findOneBy(Map<String, Object> restrictions) {
        return (T) getCriteria(restrictions).uniqueResult();
    }

    public List<T> findAll() {
        return list(getCriteria());
    }

    public List<T> findAllOrderedByPriority() {
        return findAllOrderedBy("priority");
    }

    public List<T> findAllOrderedBy(String orderBy) {
        return list(getCriteria().addOrder(Order.asc(orderBy)));
    }

    protected List<T> findAllBy(Map<String, Object> restrictions) {
        return list(getCriteria(restrictions));
    }

    protected List<T> list(Criteria criteria) {
        return criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    protected long countBy(Map<String, Object> restrictions) {
        return (long) getCriteria(restrictions).setProjection(Projections.rowCount()).uniqueResult();
    }

    protected long sumBy(String propertyName, Criteria criteria) {
        criteria.setProjection(Projections.sum(propertyName));
        Long sum = (Long) criteria.uniqueResult();
        return sum != null ? sum : 0L;
    }

    protected Criteria getCriteria() {
        return getCurrentSession().createCriteria(klass);
    }

    protected Criteria getCriteria(Map<String, Object> restrictions) {
        Criteria criteria = getCriteria();
        restrictions.entrySet().forEach(
                restriction -> criteria.add(Restrictions.eq(restriction.getKey(), restriction.getValue()))
        );
        return criteria;
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
