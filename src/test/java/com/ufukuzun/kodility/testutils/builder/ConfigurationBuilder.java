package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.configuration.Configuration;
import org.thymeleaf.util.StringUtils;

public class ConfigurationBuilder extends AbstractEntityBuilder<Configuration, ConfigurationBuilder> {

    private String key = StringUtils.randomAlphanumeric(10);

    private String value = StringUtils.randomAlphanumeric(16);

    public Configuration doBuild() {
        Configuration configuration = new Configuration();
        configuration.setName(key);
        configuration.setValue(value);
        return configuration;
    }

    public ConfigurationBuilder key(String key) {
        this.key = key;
        return this;
    }

    public ConfigurationBuilder value(String value) {
        this.value = value;
        return this;
    }

}
