package com.ufukuzun.kodility.dao.configuration;

import com.ufukuzun.kodility.dao.AbstractHibernateDao;
import com.ufukuzun.kodility.domain.configuration.Configuration;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurationDao extends AbstractHibernateDao<Configuration> {

    protected ConfigurationDao() {
        super(Configuration.class);
    }

}
