package com.ufukuzun.kodility.configuration;

import com.ufukuzun.kodility.interceptor.CommonViewParamsInterceptor;
import com.ufukuzun.kodility.interceptor.SetLocaleInterceptor;
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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.WebContentInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Locale;
import java.util.Properties;

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
            resourceHandlerRegistration.setCachePeriod(DateUtils.ONE_WEEK);
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

}