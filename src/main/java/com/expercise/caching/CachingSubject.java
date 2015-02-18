package com.expercise.caching;

import com.expercise.service.challenge.SolutionCountService;
import com.expercise.service.configuration.ConfigurationService;
import com.expercise.service.quote.QuoteService;

public enum CachingSubject {

    Configuration(ConfigurationService.class),
    SolutionCount(SolutionCountService.class),
    Quote(QuoteService.class);

    private Class<? extends Caching> cachingClass;

    private CachingSubject(Class<? extends Caching> cachingClass) {
        this.cachingClass = cachingClass;
    }

    public Class<? extends Caching> getCachingClass() {
        return cachingClass;
    }

}
