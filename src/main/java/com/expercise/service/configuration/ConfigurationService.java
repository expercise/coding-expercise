package com.expercise.service.configuration;

import com.expercise.caching.Caching;
import com.expercise.domain.configuration.Configuration;
import com.expercise.repository.configuration.ConfigurationDao;
import com.expercise.utils.EnvironmentUtils;
import com.expercise.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConfigurationService implements Caching {

    private static Map<String, String> CONFIGURATIONS;

    @Autowired
    private ConfigurationDao configurationDao;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${coding-expercise.googleAnalytics.applicationKey}")
    private String googleAnalyticsApplicationKey;

    @PostConstruct
    public void init() {
        populateConfigurationsMap();
    }

    public String getValue(String key) {
        return CONFIGURATIONS.get(key);
    }

    public String getGoogleAnalyticsApplicationKey() {
        return googleAnalyticsApplicationKey;
    }

    public String getSlackIncomingWebhookUrl() {
        return getValue("slack.incomingWebhookUrl");
    }

    public Optional<Long> getIdOfDefaultChallenge() {
        Long challengeId = getValueAsLong("default.challengeId");
        return Optional.ofNullable(challengeId);
    }

    public Long getValueAsLong(String key) {
        return NumberUtils.parseLong(getValue(key));
    }

    public boolean isDevelopment() {
        return EnvironmentUtils.isDevelopment(environment);
    }

    private void populateConfigurationsMap() {
        new TransactionTemplate(transactionManager).execute(status -> {
            CONFIGURATIONS = configurationDao.findAll().stream().collect(Collectors.toMap(Configuration::getName, Configuration::getValue));
            return null;
        });
    }

    @Override
    public void flush() {
        populateConfigurationsMap();
    }

}
