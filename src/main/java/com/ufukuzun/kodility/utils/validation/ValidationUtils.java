package com.ufukuzun.kodility.utils.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static List<String> extractAllErrorCodes(BindingResult bindingResult) {
        List<String> errorCodes = new ArrayList<>();
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorCodes.add(error.getCode());
        }
        return errorCodes;
    }

}
