package com.ufukuzun.kodility.controller.challenge.model;

public class SaveChallengeResponse {

    private boolean success = false;

    public static SaveChallengeResponse successResponse() {
        SaveChallengeResponse response = new SaveChallengeResponse();
        response.setSuccess(true);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
