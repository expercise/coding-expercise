package com.expercise.service.challenge.action.postaction;

import com.expercise.domain.user.User;
import com.expercise.service.challenge.action.PostEvaluationAction;
import com.expercise.service.challenge.model.ChallengeEvaluationContext;
import com.expercise.service.notification.SlackMessage;
import com.expercise.service.notification.SlackNotificationService;
import com.expercise.service.user.AuthenticationService;
import com.expercise.service.util.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationWhenChallengeCompletedPostAction implements PostEvaluationAction {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SlackNotificationService slackNotificationService;

    @Autowired
    private UrlService urlService;

    @Override
    public boolean canExecute(ChallengeEvaluationContext context) {
        return true;
    }

    @Override
    public void execute(ChallengeEvaluationContext context) {
        String statusPart = "is trying to solve the challenge";
        if (context.isChallengeCompleted()) {
            statusPart = "completed the challenge";
        }

        String userPart = "A guest user";
        if (authenticationService.isCurrentUserAuthenticated()) {
            User currentUser = authenticationService.getCurrentUser();
            userPart = String.format(
                    "<%s|%s>",
                    urlService.createUrlFor(currentUser.getBookmarkableUrl()),
                    currentUser.getFullName()
            );
        }

        SlackMessage slackMessage = new SlackMessage();
        slackMessage.setChannel("#general");
        slackMessage.setText(String.format(
                "%s %s <%s|%s>.",
                userPart,
                statusPart,
                urlService.challengeUrl(context.getChallenge()),
                context.getChallenge().getTitle()
        ));

        slackNotificationService.sendMessage(slackMessage);
    }

    @Override
    public int getPriority() {
        return PostEvaluationActionOrder.NOTIFY_CHALLENGE_COMPLETED.ordinal();
    }

}
