package com.expercise.dao.configuration;

import com.expercise.dao.AbstractHibernateDao;
import com.expercise.domain.configuration.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDao extends AbstractHibernateDao<Configuration> {

    protected ConfigurationDao() {
        super(Configuration.class);
    }

}
