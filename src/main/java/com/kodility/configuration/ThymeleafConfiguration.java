package com.kodility.configuration;

import com.kodility.utils.EnvironmentUtils;
import com.ufukuzun.myth.dialect.MythDialect;
import com.ufukuzun.myth.dialect.bean.Myth;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Value("${environment}")
    private String environment;

    @Bean
    public TemplateResolver webTemplateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setOrder(2);
        templateResolver.setCacheable(EnvironmentUtils.isNotDevelopment(environment));
        return templateResolver;
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

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(emailTemplateResolver());
        templateEngine.addTemplateResolver(webTemplateResolver());
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new SpringSecurityDialect());
        templateEngine.addDialect(new MythDialect());
        return templateEngine;
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public Myth myth() {
        return new Myth();
    }

}