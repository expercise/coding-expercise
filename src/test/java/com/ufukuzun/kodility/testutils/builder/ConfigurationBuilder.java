package com.ufukuzun.kodility.testutils.builder;

import com.ufukuzun.kodility.domain.configuration.Configuration;
import org.thymeleaf.util.StringUtils;

public class ConfigurationBuilder {

    private String id;
    private String key = StringUtils.randomAlphanumeric(10);
    private String value = StringUtils.randomAlphanumeric(16);

    public Configuration build() {
        Configuration configuration = new Configuration();
        configuration.setId(id);
        configuration.setKey(key);
        configuration.setValue(value);
        return configuration;
    }

    public String getId() {
        return id;
    }

    public ConfigurationBuilder id(String id) {
        this.id = id;
        return this;
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
