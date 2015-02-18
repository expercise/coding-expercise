package com.expercise.controller.caching;

import com.expercise.caching.Caching;
import com.expercise.caching.CachingSubject;
import com.expercise.controller.BaseManagementController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CachingController extends BaseManagementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachingController.class);

    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/flushCache/{cachingSubject}")
    @ResponseBody
    public String flushCache(@PathVariable String cachingSubject) {
        try {
            Caching cachingBean = applicationContext.getBean(CachingSubject.valueOf(cachingSubject).getCachingClass());
            cachingBean.flush();
        } catch (Exception e) {
            LOGGER.error("Exception occurred while flushing caching subject: " + cachingSubject, e);
            return HttpStatus.BAD_REQUEST.getReasonPhrase();
        }

        return HttpStatus.OK.getReasonPhrase();
    }

}
