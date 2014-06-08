package com.ufukuzun.kodility.service.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConfigurationMap {

    private static final Map<String, String> map = new ConcurrentHashMap<String, String>() {{
        put("challenge.defaultpointamount", "10");
    }};


    public static Map<String, String> getMap() {
        return map;
    }

}
