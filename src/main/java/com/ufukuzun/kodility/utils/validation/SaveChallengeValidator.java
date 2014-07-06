package com.ufukuzun.kodility.utils.validation;

import com.ufukuzun.kodility.controller.challenge.model.ChallengeModel;
import com.ufukuzun.kodility.domain.challenge.Challenge;
import com.ufukuzun.kodility.domain.user.User;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import com.ufukuzun.kodility.service.challenge.ChallengeService;
import com.ufukuzun.kodility.service.user.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

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
        }
        return true;
    }

    private void addAuthorityError(BindingResult bindingResult, String errorCode) {
        addError(bindingResult, "currentUser", new String[]{errorCode});
    }

    private void validateTitlesAndDescriptions(ChallengeModel challenge, BindingResult bindingResult) {
        for (Lingo lingo : Lingo.values()) {
            boolean noTitleOrDescriptionForThisLingo = StringUtils.isBlank(challenge.getDescriptionFor(lingo)) || StringUtils.isBlank(challenge.getTitleFor(lingo));
            if (noTitleOrDescriptionForThisLingo) {
                addError(bindingResult, "titlesAndDescriptions", new String[]{"NotEmpty.challenge.titlesAndDescriptions"});
                break;
            }
        }
    }

    private void validateOutputTypeWithOutputValues(ChallengeModel challenge, BindingResult bindingResult) {
        DataType outputType = challenge.getOutputType();
        boolean valid = validateField(challenge.hasOutputType(), bindingResult, "challenge", "outputType", outputType, new String[]{"NotEmpty.challenge.outputType"});
        if (valid) {
            for (ChallengeModel.TestCase testCase : challenge.getTestCases()) {
                String outputValue = testCase.getOutputValue();
                if (outputValue == null || outputType.isNotProperTypeFor(outputValue)) {
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
        return challengeModel.isApproved() != null && challenge.isApproved() != challengeModel.isApproved();
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
