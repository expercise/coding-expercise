package com.expercise.utils.validation;

import com.expercise.controller.challenge.model.ChallengeModel;
import com.expercise.domain.challenge.Challenge;
import com.expercise.domain.user.User;
import com.expercise.enums.DataType;
import com.expercise.enums.Lingo;
import com.expercise.exception.ExperciseGenericException;
import com.expercise.service.challenge.ChallengeService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Stream;

@Component
public class SaveChallengeValidator extends AbstractValidator<ChallengeModel> {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ChallengeService challengeService;

    @Override
    public void validate(ChallengeModel challenge, BindingResult bindingResult) {
        if (!validateIfUserCanModifyThisChallenge(challenge, bindingResult)) {
            return;
        }
        validateTitlesAndDescriptions(challenge, bindingResult);
        validateOutputTypeWithOutputValues(challenge, bindingResult);
        validateInputTypesWithInputValuesFromTestCases(challenge, bindingResult);
    }

    private boolean validateIfUserCanModifyThisChallenge(ChallengeModel challengeModel, BindingResult bindingResult) {
        Long challengeId = challengeModel.getChallengeId();
        if (challengeId != null) {
            Challenge challenge = challengeService.findById(challengeId);
            User currentUser = authenticationService.getCurrentUser();
            if (currentUser.isNotAdmin() && isModifierNotSameUserWithAuthor(challenge, currentUser)) {
                addAuthorityError(bindingResult, "NotAuthorized.challenge.update");
                return false;
            }

            if (currentUser.isNotAdmin() && isApproveStatusChanged(challengeModel, challenge)) {
                addAuthorityError(bindingResult, "NotAuthorized.challenge.changeApproveStatus");
                return false;
            }

            if (currentUser.isNotAdmin() && isLevelChanged(challengeModel, challenge)) {
                addAuthorityError(bindingResult, "NotAuthorized.challenge.changeLevel");
                return false;
            }
        }
        return true;
    }

    private void addAuthorityError(BindingResult bindingResult, String errorCode) {
        addError(bindingResult, "currentUser", new String[]{errorCode});
    }

    private void validateTitlesAndDescriptions(ChallengeModel challenge, BindingResult bindingResult) {
        long lingoCountWithTitleAndDescription = Stream.of(Lingo.values())
                .filter(challenge::isSupportedLingo)
                .count();

        if (lingoCountWithTitleAndDescription == 0) {
            addError(bindingResult, "titlesAndDescriptions", new String[]{"NotEmpty.challenge.titlesAndDescriptions"});
        }
    }

    private void validateOutputTypeWithOutputValues(ChallengeModel challenge, BindingResult bindingResult) {
        DataType outputType = challenge.getOutputType();
        boolean valid = validateField(challenge.hasOutputType(), bindingResult, "challenge", "outputType", outputType, new String[]{"NotEmpty.challenge.outputType"});
        if (valid) {
            for (ChallengeModel.TestCase testCase : challenge.getTestCases()) {
                try {
                    testCase.setOutputValue(JsonUtils.format(testCase.getOutputValue()));
                } catch (ExperciseGenericException e) {
                    addError(bindingResult, "outputValues", new String[]{"NotValid.challenge.outputValues"});
                    break;
                }
            }
        }
    }

    private void validateInputTypesWithInputValuesFromTestCases(ChallengeModel challenge, BindingResult bindingResult) {
        List<ChallengeModel.TestCase> testCases = challenge.getTestCases();
        boolean testCasesExist = validateField(!testCases.isEmpty(), bindingResult, "challenge", "testCases", testCases, new String[]{"NotEmpty.challenge.testCases"});
        if (testCasesExist) {
            for (ChallengeModel.TestCase testCase : testCases) {
                if (testCase.getInputValues().size() != challenge.getInputTypes().size() || isInputValuesNotProperFoInputTypes(challenge.getInputTypes(), testCase.getInputValues())) {
                    addError(bindingResult, "inputValues", new String[]{"NotValid.challenge.inputValues"});
                    break;
                }
            }
        }
    }

    private boolean isModifierNotSameUserWithAuthor(Challenge challenge, User currentUser) {
        return !challenge.getUser().equals(currentUser);
    }

    private boolean isApproveStatusChanged(ChallengeModel challengeModel, Challenge challenge) {
        return challengeModel.getApproved() != null && challenge.isApproved() != challengeModel.getApproved();
    }

    private boolean isLevelChanged(ChallengeModel challengeModel, Challenge challenge) {
        return challengeModel.getLevel() != null && challenge.getLevelId() != challengeModel.getLevel();
    }

    private boolean isInputValuesNotProperFoInputTypes(List<DataType> inputTypes, List<String> inputValues) {
        if (inputTypes.size() == inputValues.size()) {
            for (int index = 0; index < inputTypes.size(); index++) {
                if (inputTypes.get(index).isNotProperTypeFor(inputValues.get(index))) {
                    return true;
                }
            }
        }
        return false;
    }

}
