package com.kodility.dao.configuration;

import com.kodility.dao.AbstractHibernateDao;
import com.kodility.domain.configuration.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDao extends AbstractHibernateDao<Configuration> {

    protected ConfigurationDao() {
        super(Configuration.class);
    }

}
