package com.kodility.testutils.builder;

import com.kodility.domain.configuration.Configuration;

public class ConfigurationBuilder extends AbstractEntityBuilder<Configuration, ConfigurationBuilder> {

    private String key;

    private String value;

    @Override
    protected Configuration doBuild() {
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
