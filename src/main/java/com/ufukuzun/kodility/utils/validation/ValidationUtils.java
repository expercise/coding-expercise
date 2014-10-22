package com.ufukuzun.kodility.utils.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static List<String> extractAllErrorCodes(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getCode)
                .collect(Collectors.toList());
    }

}
