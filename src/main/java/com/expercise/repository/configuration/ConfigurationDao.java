package com.expercise.repository.configuration;

import com.expercise.repository.AbstractHibernateDao;
import com.expercise.domain.configuration.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDao extends AbstractHibernateDao<Configuration> {

    protected ConfigurationDao() {
        super(Configuration.class);
    }

}
