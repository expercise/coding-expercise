package com.kodility.service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Value("${rootUrl}")
    private String rootUrl;

    public String createUrlFor(String path) {
        return rootUrl + path;
    }

}
