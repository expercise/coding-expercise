package com.expercise.interpreter.typechecker;

import com.expercise.enums.DataType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class TypeChecker {

    private final Map<DataType, Class<?>> javaDataTypeMapping = new HashMap<>();

    @PostConstruct
    public void init() {
        javaDataTypeMapping.put(DataType.Integer, Integer.class);
        javaDataTypeMapping.put(DataType.Text, String.class);
    }

    public boolean typeCheck(Object value, DataType dataType) {
        Class<?> javaClassOfType = javaDataTypeMapping.get(dataType);
        return javaClassOfType.isInstance(value);
    }

}
