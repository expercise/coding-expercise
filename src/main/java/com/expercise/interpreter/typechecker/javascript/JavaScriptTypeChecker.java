package com.expercise.interpreter.typechecker.javascript;

import com.expercise.enums.DataType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JavaScriptTypeChecker {

    private final Map<DataType, BasicJavaTypeWrapper> javaDataTypeMapping = new HashMap<>();

    @PostConstruct
    public void init() {
        javaDataTypeMapping.put(DataType.Integer, new BasicJavaTypeWrapper(Number.class));
        javaDataTypeMapping.put(DataType.Text, new BasicJavaTypeWrapper(String.class));
        javaDataTypeMapping.put(DataType.Array, new ListJavaTypeWrapper(List.class));
    }

    public boolean typeCheck(Object value, DataType dataType) {
        return javaDataTypeMapping.get(dataType).valid(value);
    }

}