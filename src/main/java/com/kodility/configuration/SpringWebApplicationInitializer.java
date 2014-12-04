package com.kodility.configuration;

import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import java.util.EnumSet;

@SuppressWarnings("UnusedDeclaration")
public class SpringWebApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(SpringWebMvcConfiguration.class);
        webApplicationContext.setDisplayName("Kodility");

        FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
        characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
        characterEncodingFilter.setInitParameter("encoding", "UTF-8");
        characterEncodingFilter.setInitParameter("forceEncoding", "true");

        FilterRegistration.Dynamic openSessionInViewFilter = servletContext.addFilter("openSessionInViewFilter", new OpenSessionInViewFilter());
        openSessionInViewFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        String springSecurityFilterChainName = "springSecurityFilterChain";
        FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter(springSecurityFilterChainName, new DelegatingFilterProxy(springSecurityFilterChainName));
        springSecurityFilterChain.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST, DispatcherType.ERROR, DispatcherType.ASYNC), true, "/*");

        servletContext.setInitParameter("defaultHtmlEscape", "true");
        servletContext.addListener(new ContextLoaderListener(webApplicationContext));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(webApplicationContext));
        dispatcher.addMapping("/");
        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);
    }

}
