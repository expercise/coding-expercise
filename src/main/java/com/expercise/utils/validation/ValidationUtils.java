package com.expercise.utils.validation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static List<String> extractAllErrorCodes(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getCode)
                .collect(Collectors.toList());
    }

}
