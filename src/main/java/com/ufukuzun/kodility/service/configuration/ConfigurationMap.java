package com.ufukuzun.kodility.service.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigurationMap {

    private static final Map<String, String> map = new ConcurrentHashMap<String, String>() {{
    }};


    public static Map<String, String> getMap() {
        return map;
    }

}
