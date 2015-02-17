package com.expercise.configuration;

import com.expercise.service.user.UserRememberMeTokenRepository;
import com.expercise.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final int PASSWORD_ENCODING_STRENGTH = 256;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/",
                        "/404",
                        "/generatedResources/**",
                        "/resources/**",
                        "/login/**",
                        "/register/**",
                        "/forgotMyPassword/**"
                )
                .permitAll();

        http.authorizeRequests()
                .antMatchers(
                        "/challenges/**",
                        "/profile/**",
                        "/themes/**"
                )
                .hasAnyRole("USER")
                .antMatchers("/manage/**")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll();

        http.rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(DateUtils.ONE_WEEK);

        http.logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();

        http.csrf().disable();
    }

    @Bean
    public UserRememberMeTokenRepository persistentTokenRepository() {
        return new UserRememberMeTokenRepository();
    }

    @Autowired
    public void configureGlobal(@SuppressWarnings("SpringJavaAutowiringInspection") AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(shaPasswordEncoder());
    }

    @Bean
    public ShaPasswordEncoder shaPasswordEncoder() {
        return new ShaPasswordEncoder(PASSWORD_ENCODING_STRENGTH);
    }

}
