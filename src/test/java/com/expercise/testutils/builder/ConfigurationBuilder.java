package com.expercise.testutils.builder;

import com.expercise.domain.configuration.Configuration;

public class ConfigurationBuilder extends BaseEntityBuilder<Configuration, ConfigurationBuilder> {

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
