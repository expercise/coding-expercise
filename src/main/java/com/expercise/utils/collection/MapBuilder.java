package com.expercise.utils.collection;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder<K, V> {

    private Map<K, V> map = new HashMap<>();

    public Map<K, V> build() {
        return map;
    }

    public MapBuilder<K, V> put(K key, V value) {
        map.put(key, value);
        return this;
    }

}
