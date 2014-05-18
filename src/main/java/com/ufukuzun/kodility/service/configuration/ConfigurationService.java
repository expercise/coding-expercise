package com.ufukuzun.kodility.service.configuration;

import com.ufukuzun.kodility.dao.configuration.ConfigurationDao;
import com.ufukuzun.kodility.domain.configuration.Configuration;
import com.ufukuzun.kodility.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigurationService {

    private static final Map<String, String> CONFIGURATIONS = new ConcurrentHashMap<>();

    @Autowired
    private ConfigurationDao configurationDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Value("${environment}")
    private String environment;

    @PostConstruct
    public void init() {
        populateConfigurationsMap();
    }

    public String getValue(String key) {
        return CONFIGURATIONS.get(key);
    }

    public String getGoogleAnalyticsScript() {
        return getValue("google.analytics.script");
    }

    public boolean isProduction() {
        return EnvironmentUtils.isProduction(environment);
    }

    private void populateConfigurationsMap() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus status) {
                for (Configuration configuration : configurationDao.findAll()) {
                    CONFIGURATIONS.put(configuration.getName(), configuration.getValue());
                }
                return null;
            }
        });
    }

}
