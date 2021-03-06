package com.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.core.userdetails.User;
/**
 * Created by Ben_Big on 7/7/16.
 */


@Configuration
@EnableWebSecurity
@ComponentScan("com.login")
public class SecurityJavaConfig extends WebSecurityConfigurerAdapter{
    //It depends on MySavedRequestAwareAuthenticationSuccessHandler
    @Autowired
    private MySavedRequestAwareAuthenticationSuccessHandler  authenticationSuccessHandler;


    //It also depends on RestAuthenticationEntryPoint
    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private UserrDAOImpl userrDAO;


    public SecurityJavaConfig(){
        super();
    }

    @Override
    protected  void configure(final AuthenticationManagerBuilder auth)
        throws Exception{


        auth.userDetailsService(userrDAO);

        /*auth.inMemoryAuthentication()
                .withUser("temporary").password("temporary").roles("ADMIN")
                .and()
                .withUser("AlabamaMan").password("userPass").roles("USER")
                ;*/

    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/foos").authenticated()
                .antMatchers("/api/bars").authenticated()
                .antMatchers("/user").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout();
    }


    @Bean
    public MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler() {
        return new MySavedRequestAwareAuthenticationSuccessHandler();
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler myFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }



}
