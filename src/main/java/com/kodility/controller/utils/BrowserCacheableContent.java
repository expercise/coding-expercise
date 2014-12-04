package com.kodility.controller.utils;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BrowserCacheableContent {

    public abstract String generateContent();

    public String getContent(HttpServletRequest request, HttpServletResponse response) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");

        if (ifModifiedSince > -1) {
            response.setStatus(HttpStatus.NOT_MODIFIED.value());
            return "";
        }

        response.addDateHeader("Last-Modified", System.currentTimeMillis());

        return generateContent();
    }

}
