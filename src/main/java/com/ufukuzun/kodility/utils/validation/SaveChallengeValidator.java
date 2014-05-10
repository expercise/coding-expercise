package com.ufukuzun.kodility.utils.validation;

import com.ufukuzun.kodility.controller.challenge.model.SaveChallengeRequest;
import com.ufukuzun.kodility.enums.DataType;
import com.ufukuzun.kodility.enums.Lingo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.List;

@Component
public class SaveChallengeValidator extends AbstractValidator<SaveChallengeRequest> {

    @Override
    public void validate(SaveChallengeRequest challenge, BindingResult bindingResult) {
        validateTitlesAndDescriptions(challenge, bindingResult);
        validateOutputTypeWithOutputValues(challenge, bindingResult);
        validateInputTypesWithInputValuesFromTestCases(challenge, bindingResult);
    }

    private void validateTitlesAndDescriptions(SaveChallengeRequest challenge, BindingResult bindingResult) {
        for (Lingo lingo : Lingo.values()) {
            boolean noTitleOrDescriptionForThisLingo = StringUtils.isBlank(challenge.getDescriptionFor(lingo)) || StringUtils.isBlank(challenge.getTitleFor(lingo));
            if (noTitleOrDescriptionForThisLingo) {
                addError(bindingResult, "titlesAndDescriptions", new String[]{"NotEmpty.challenge.titlesAndDescriptions"});
                break;
            }
        }
    }

    private void validateOutputTypeWithOutputValues(SaveChallengeRequest challenge, BindingResult bindingResult) {
        DataType outputType = challenge.getOutputType();
        boolean valid = validateField(challenge.hasOutputType(), bindingResult, "challenge", "outputType", outputType, new String[]{"NotEmpty.challenge.outputType"});
        if (valid) {
            for (SaveChallengeRequest.TestCase testCase : challenge.getTestCases()) {
                String outputValue = testCase.getOutputValue();
                if (outputValue == null || outputType.isNotProperTypeFor(outputValue)) {
                    addError(bindingResult, "outputValues", new String[]{"NotValid.challenge.outputValues"});
                    break;
                }
            }
        }
    }

    private void validateInputTypesWithInputValuesFromTestCases(SaveChallengeRequest challenge, BindingResult bindingResult) {
        List<SaveChallengeRequest.TestCase> testCases = challenge.getTestCases();
        boolean testCasesExist = validateField(!testCases.isEmpty(), bindingResult, "challenge", "testCases", testCases, new String[]{"NotEmpty.challenge.testCases"});
        if (testCasesExist) {
            for (SaveChallengeRequest.TestCase testCase : testCases) {
                if (testCase.getInputValues().size() != challenge.getInputTypes().size() || isInputValuesNotProperFoInputTypes(challenge.getInputTypes(), testCase.getInputValues())) {
                    addError(bindingResult, "inputValues", new String[]{"NotValid.challenge.inputValues"});
                    break;
                }
            }
        }
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
