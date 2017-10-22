package com.expercise.utils.validation;

import com.expercise.controller.level.model.LevelModel;
import com.expercise.service.level.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueLevelPriorityValidator implements ConstraintValidator<UniqueLevelPriority, LevelModel> {

    @Autowired
    private LevelService levelService;

    @Override
    public void initialize(UniqueLevelPriority constraintAnnotation) {
    }

    @Override
    public boolean isValid(LevelModel levelModel, ConstraintValidatorContext context) {
        return levelModel == null || levelService.isValidToSave(levelModel.getPriority(), levelModel.getLevelId());
    }

}
