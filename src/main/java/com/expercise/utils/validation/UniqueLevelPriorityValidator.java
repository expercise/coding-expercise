package com.expercise.utils.validation;

import com.expercise.controller.level.model.SaveLevelRequest;
import com.expercise.service.level.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueLevelPriorityValidator implements ConstraintValidator<UniqueLevelPriority, SaveLevelRequest> {

    @Autowired
    private LevelService levelService;

    @Override
    public void initialize(UniqueLevelPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(SaveLevelRequest saveLevelRequest, ConstraintValidatorContext context) {
        return saveLevelRequest == null || levelService.isValidToSave(saveLevelRequest.getPriority(), saveLevelRequest.getLevelId());
    }

}
