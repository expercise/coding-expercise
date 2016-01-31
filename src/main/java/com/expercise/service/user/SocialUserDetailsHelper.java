package com.expercise.service.user;

import com.expercise.enums.SocialSignInProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;

public final class SocialUserDetailsHelper {

    private SocialUserDetailsHelper() {
    }

    public static String getImageUrl(ConnectionData connectionData) {
        String imageUrl = connectionData.getImageUrl();
        if (SocialSignInProvider.Twitter.getProviderId().equals(connectionData.getProviderId())) {
            imageUrl = imageUrl.replace("_normal", "");
        }
        return imageUrl;
    }

    public static String getFirstName(UserProfile userProfile) {
        return StringUtils.isNotBlank(userProfile.getFirstName()) ? userProfile.getFirstName() : userProfile.getName();
    }

}
