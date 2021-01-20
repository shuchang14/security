package com.shu.security.config;

import com.shu.security.filter.AfterSessionManagementFilter;
import com.shu.security.handler.AuthenctiationFailureHandler;
import com.shu.security.handler.AuthenticationSuccessHandler;
import com.shu.security.service.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.SessionManagementFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenctiationFailureHandler authenctiationFailureHandler;
    @Bean
    public SessionRegistry sessionRegistry(){
        return new SessionRegistryImpl();
    }
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
               .failureHandler(authenctiationFailureHandler)
               .and().headers().frameOptions().disable()//解决：springBoot springSecurty: x-frame-options deny禁止iframe调用
               .and().sessionManagement().maximumSessions(1).expiredUrl("/user/expired").sessionRegistry(sessionRegistry());
        http.addFilterAfter(new AfterSessionManagementFilter(),SessionManagementFilter.class);//在会话管理拦截器之后加入拦截器，拦截访问会话，更新最后访问时间
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityUserService).passwordEncoder(passwordEncoder());
    }
}
