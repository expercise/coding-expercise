package com.expercise.configuration;

import com.expercise.interceptor.CommonViewParamsInterceptor;
import com.expercise.interceptor.SetLocaleInterceptor;
import com.expercise.utils.DateUtils;
import com.expercise.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableWebMvc
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${spring.profiles.active}")
    private String environment;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new MessagesResourceBundleSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");

        if (EnvironmentUtils.isDevelopment(environment)) {
            messageSource.setCacheSeconds(0);
        }

        return messageSource;
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setInterceptors(
                setLocaleInterceptor(),
                deviceResolverHandlerInterceptor(),
                commonViewParamsInterceptor(),
                generatedResourcesCachingInterceptor()
        );
        return handlerMapping;
    }

    @Bean
    public SetLocaleInterceptor setLocaleInterceptor() {
        SetLocaleInterceptor interceptor = new SetLocaleInterceptor();
        interceptor.setParamName("lingopref");
        return interceptor;
    }

    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public HandlerInterceptorAdapter commonViewParamsInterceptor() {
        return new CommonViewParamsInterceptor();
    }

    @Bean
    public WebContentInterceptor generatedResourcesCachingInterceptor() {
        WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
        Properties cacheMappings = new Properties();
        cacheMappings.setProperty("/generatedResources/**", String.valueOf(DateUtils.ONE_WEEK));
        webContentInterceptor.setCacheMappings(cacheMappings);
        webContentInterceptor.setUseCacheControlHeader(true);
        webContentInterceptor.setAlwaysMustRevalidate(true);
        webContentInterceptor.setUseExpiresHeader(true);
        return webContentInterceptor;
    }

}