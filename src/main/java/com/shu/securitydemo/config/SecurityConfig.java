package com.shu.securitydemo.config;

import com.shu.securitydemo.handler.AuthenctiationFailureHandler;
import com.shu.securitydemo.handler.AuthenticationSuccessHandler;
import com.shu.securitydemo.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenctiationFailureHandler authenctiationFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
               .authorizeRequests()
               .antMatchers("/user/register")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginProcessingUrl("/login")
               .successHandler(authenticationSuccessHandler)
               .failureHandler(authenctiationFailureHandler);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }
}
