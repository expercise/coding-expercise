package com.ufukuzun.kodility.service.configuration;

import com.ufukuzun.kodility.dao.configuration.ConfigurationDao;
import com.ufukuzun.kodility.domain.configuration.Configuration;
import com.ufukuzun.kodility.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigurationService {

    private static final Map<String, String> CONFIGURATIONS = new ConcurrentHashMap<>();

    @Autowired
    private ConfigurationDao configurationDao;

    @Value("${environment}")
    private String environment;

    @PostConstruct
    public void init() {
        for (Configuration configuration : configurationDao.findAll()) {
            CONFIGURATIONS.put(configuration.getKey(), configuration.getValue());
        }
    }

    public String getGoogleAnalyticsScript() {
        return CONFIGURATIONS.get("google.analytics.script");
    }

    public boolean isProduction() {
        return EnvironmentUtils.isProduction(environment);
    }

}
