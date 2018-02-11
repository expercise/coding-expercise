package com.expercise.service.configuration;

import com.expercise.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configurations {

    @Value("${spring.profiles.active}")
    private String environment;

    @Value("${coding-expercise.googleAnalytics.applicationKey}")
    private String googleAnalyticsApplicationKey;

    @Value("${coding-expercise.slack.incomingWebhookUrl}")
    private String slackIncomingWebhookUrl;

    @Value("${coding-expercise.defaults.challengeId}")
    private Long defaultChallengeId;

    @Value("${coding-expercise.challenge-approval-strategy}")
    private String challengeApprovalStrategy;

    public String getGoogleAnalyticsApplicationKey() {
        return googleAnalyticsApplicationKey;
    }

    public String getSlackIncomingWebhookUrl() {
        return slackIncomingWebhookUrl;
    }

    public Long getDefaultChallengeId() {
        return defaultChallengeId;
    }

    public boolean isDevelopment() {
        return EnvironmentUtils.isDevelopment(environment);
    }

    public String getChallengeApprovalStrategy() {
        return challengeApprovalStrategy;
    }
}
