package com.example.security.security;

import com.example.security.filter.CustomAuthenticationFilter;
import com.example.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //disable cross site request forgery
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        //FOR ALL USERS
        http.authorizeRequests().antMatchers( "api/login", "/api/token/refresh/**").permitAll()
                .antMatchers( "/api/roles/**").permitAll()
                .antMatchers( "/api/users/**").permitAll()
                .antMatchers( "/api/userProfiles/**").permitAll()
                .antMatchers( "/api/userTests/**").permitAll()
                .antMatchers( "/api/comments/**").permitAll()
                .antMatchers( "/api/profileImages/**").permitAll()
                .antMatchers( "/api/userTests/**").permitAll();

        //TABLE TOPICS ->PROFESSOR AND ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/topics/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(PUT, "/api/topics/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(DELETE, "/api/topics/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN");

        //TABLE EDUCATION LEVELS ->ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/educationLevels/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(PUT, "/api/educationLevels/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(DELETE, "/api/educationLevels/**").hasAuthority("ROLE_ADMIN");

        //TABLE TESTS -> PROFESSOR AND ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/tests/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(PUT, "/api/tests/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(DELETE, "/api/tests/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN");

        //TABLE FILE SOLUTIONS -> STUDENT AND ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/fileSolutions/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN")
                .antMatchers(PUT, "/api/fileSolutions/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN")
                .antMatchers(DELETE, "/api/fileSolutions/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_ADMIN");

        //TABLE MEETINGS -> PROFESSOR AND ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/meetings/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(PUT, "/api/meetings/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
                .antMatchers(DELETE, "/api/meetings/**").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN");

        //TABLE USER ARRIVALS ->USER AND ADMIN
        http.authorizeRequests().antMatchers(POST, "/api/userarrivals/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers(PUT, "/api/userarrivals/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .antMatchers(DELETE, "/api/userarrivals/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN");

//        http.authorizeRequests().anyRequest().authenticated();
        //filters
        CustomAuthenticationFilter customAuthenticationFilter= new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login"); //setting login api if it is needed
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), CustomAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
