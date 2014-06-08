package com.ufukuzun.kodility.service.configuration;

import com.ufukuzun.kodility.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConfigurationService {

    private static final Map<String, String> CONFIGURATIONS = new ConcurrentHashMap<>();

    @Value("${environment}")
    private String environment;

    @PostConstruct
    public void init() {
        populateConfigurationsMap();
    }

    public String getValue(String key) {
        return CONFIGURATIONS.get(key);
    }

    public int getValueAsInteger(String key) {
        return Integer.parseInt(getValue(key));
    }

    public String getGoogleAnalyticsScript() {
        return getValue("google.analytics.script");
    }

    public boolean isProduction() {
        return EnvironmentUtils.isProduction(environment);
    }

    private void populateConfigurationsMap() {
        CONFIGURATIONS.putAll(ConfigurationMap.getMap());
    }

}
