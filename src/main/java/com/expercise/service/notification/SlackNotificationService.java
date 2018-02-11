package com.expercise.service.notification;

import com.expercise.service.configuration.Configurations;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SlackNotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackNotificationService.class);

    @Autowired
    private Configurations configurations;

    @Async
    public void sendMessage(SlackMessage slackMessage) {
        String slackIncomingWebhookUrl = configurations.getSlackIncomingWebhookUrl();

        if (StringUtils.isBlank(slackIncomingWebhookUrl)) {
            LOGGER.warn("No slack incoming webhook url configured!");
            return;
        }

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-type", MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8");

            HttpEntity<SlackMessage> httpEntity = new HttpEntity<>(slackMessage, httpHeaders);

            new RestTemplate().postForLocation(slackIncomingWebhookUrl, httpEntity);
        } catch (Exception e) {
            LOGGER.error("Slack notification couldn't sent: " + slackMessage, e);
        }
    }

}
