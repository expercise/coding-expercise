package com.ufukuzun.kodility.configuration;

import com.ufukuzun.kodility.interceptor.CommonViewParamsInterceptor;
import com.ufukuzun.kodility.utils.DateUtils;
import com.ufukuzun.kodility.utils.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan("com.ufukuzun.kodility")
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${environment}")
    private String environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

        if (EnvironmentUtils.isNotDevelopment(environment)) {
            resourceHandlerRegistration.setCachePeriod(DateUtils.ONE_DAY);
        }
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new MessagesResourceBundleSource();
        messageSource.setBasename("messages");
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
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lingopref");
        return localeChangeInterceptor;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setInterceptors(new Object[]{localeChangeInterceptor(), commonViewParamsInterceptor()});
        return handlerMapping;
    }

    @Bean
    public HandlerInterceptorAdapter commonViewParamsInterceptor() {
        return new CommonViewParamsInterceptor();
    }

}