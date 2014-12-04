package com.kodility.utils.validation;

import com.kodility.service.challenge.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueLevelPriorityValidator implements ConstraintValidator<UniqueLevelPriority, Integer> {

    @Autowired
    private LevelService levelService;

    @Override
    public void initialize(UniqueLevelPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null || levelService.getByPriority(value) == null;
    }

}
