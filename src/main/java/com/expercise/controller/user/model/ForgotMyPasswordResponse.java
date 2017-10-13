package com.expercise.controller.user.model;

public class ForgotMyPasswordResponse {

    private boolean success;

    private String messageKey;

    public static ForgotMyPasswordResponse failedResponse() {
        ForgotMyPasswordResponse failedResponse = new ForgotMyPasswordResponse();
        failedResponse.setSuccess(false);
        failedResponse.setMessageKey("forgotMyPassword.request.failed");
        return failedResponse;
    }

    public static ForgotMyPasswordResponse successResponse() {
        ForgotMyPasswordResponse successResponse = new ForgotMyPasswordResponse();
        successResponse.setSuccess(true);
        successResponse.setMessageKey("forgotMyPassword.request.success");
        return successResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

}
