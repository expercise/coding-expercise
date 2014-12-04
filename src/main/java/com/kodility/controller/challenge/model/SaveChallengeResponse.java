package com.kodility.controller.challenge.model;

import java.util.ArrayList;
import java.util.List;

public class SaveChallengeResponse {

    private boolean success = false;

    private Long challengeId;

    private List<String> errorCodes = new ArrayList<>();

    public static SaveChallengeResponse successResponse(Long challengeId) {
        SaveChallengeResponse response = new SaveChallengeResponse();
        response.setSuccess(true);
        response.setChallengeId(challengeId);
        return response;
    }

    public static SaveChallengeResponse failedResponse(List<String> errorCodes) {
        SaveChallengeResponse response = new SaveChallengeResponse();
        response.setSuccess(false);
        response.setErrorCodes(errorCodes);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Long challengeId) {
        this.challengeId = challengeId;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }

}
