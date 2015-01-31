package com.expercise.configuration;

import com.expercise.interceptor.CommonViewParamsInterceptor;
import com.expercise.interceptor.SetLocaleInterceptor;
import com.expercise.utils.DateUtils;
import com.expercise.utils.EnvironmentUtils;
import com.ufukuzun.myth.dialect.handler.AjaxRequestResponseBodyReturnValueHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan("com.expercise")
public class SpringWebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Value("${environment}")
    private String environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");

        if (EnvironmentUtils.isNotDevelopment(environment)) {
            resourceHandlerRegistration.setCachePeriod(DateUtils.ONE_WEEK);
        }
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new MessagesResourceBundleSource();
        messageSource.setBasenames("messages", "emails");
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
    public SetLocaleInterceptor setLocaleInterceptor() {
        SetLocaleInterceptor interceptor = new SetLocaleInterceptor();
        interceptor.setParamName("lingopref");
        return interceptor;
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setInterceptors(new Object[]{setLocaleInterceptor(), commonViewParamsInterceptor(), generatedResourcesCachingInterceptor()});
        return handlerMapping;
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

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(ajaxRequestResponseBodyReturnValueHandler());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(ajaxRequestResponseBodyReturnValueHandler());
    }

    @Bean
    public AjaxRequestResponseBodyReturnValueHandler ajaxRequestResponseBodyReturnValueHandler() {
        return new AjaxRequestResponseBodyReturnValueHandler(Arrays.asList(mappingJackson2HttpMessageConverter()));
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter();
    }

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}