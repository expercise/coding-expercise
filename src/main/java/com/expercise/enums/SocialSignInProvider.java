package com.expercise.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum SocialSignInProvider {

    Twitter("twitter"),
    Facebook("facebook"),
    LinkedIn("linkedin"),
    GitHub("github"),
    Google("google");

    private final String providerId;

    SocialSignInProvider(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderId() {
        return providerId;
    }

    public static Optional<SocialSignInProvider> getForProviderId(String providerId) {
        return Stream.of(values())
                .filter(it -> it.providerId.equals(providerId))
                .findFirst();
    }

}