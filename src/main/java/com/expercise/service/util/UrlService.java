package com.expercise.service.util;

import com.expercise.domain.challenge.Challenge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UrlService {

    @Value("${coding-expercise.root-url}")
    private String rootUrl;

    public String createUrlFor(String path) {
        return getRootUrl() + path;
    }

    public String getCanonical(HttpServletRequest request) {
        String uri = request.getRequestURI().replaceFirst("/expercise", "");
        return getRootUrl() + uri;
    }

    private String getRootUrl() {
        return rootUrl.replaceFirst("://www.", "://");
    }

    public String updateChallenge(Challenge challenge) {
        return "/challenges/updateChallenge/" + challenge.getId();
    }

}
