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

    private final Class clazz;
    @Autowired
    private SessionFactory sessionFactory;

    protected AbstractHibernateDao(Class clazz) {
        this.clazz = clazz;
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
        return (T) getCurrentSession().get(clazz, id);
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
        return countBy(getCriteria(restrictions));
    }

    protected long countBy(Criteria criteria) {
        Long result = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        return result != null ? result : 0;
    }

    protected long sumBy(String propertyName, Criteria criteria) {
        criteria.setProjection(Projections.sum(propertyName));
        Long sum = (Long) criteria.uniqueResult();
        return sum != null ? sum : 0L;
    }

    protected Criteria getCriteria() {
        return getCurrentSession().createCriteria(clazz);
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
