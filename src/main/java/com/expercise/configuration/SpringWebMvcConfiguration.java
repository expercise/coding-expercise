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
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.util.Locale;
import java.util.Properties;

@Configuration
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(setLocaleInterceptor());
        registry.addInterceptor(deviceResolverHandlerInterceptor());
        registry.addInterceptor(commonViewParamsInterceptor());
        registry.addInterceptor(generatedResourcesCachingInterceptor());
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

    @Bean
    public TemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("emails/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setTemplateMode("HTML5");
        classLoaderTemplateResolver.setOrder(1);
        classLoaderTemplateResolver.setCacheable(EnvironmentUtils.isNotDevelopment(environment));
        return classLoaderTemplateResolver;
    }

}