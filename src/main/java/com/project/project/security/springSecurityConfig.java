package com.project.project.security;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class springSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // 禁用CSRF，确认不需要使用
                .headers().cacheControl().disable().and()
                .authorizeRequests()
                .antMatchers("/api/auth/generateCaptcha", "/api/auth/login").permitAll()
                .antMatchers("/static/**", "/swagger-ui/**", "/v2/api-docs").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/attachment/**", "/profile/**").permitAll()
                .antMatchers(HttpMethod.POST, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/attachment/**", "/profile/**").permitAll()
                .anyRequest().authenticated() // 其他请求都需要认证
                .and()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) ->
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .accessDeniedHandler((request, response, accessDeniedException) ->
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden"));
    }
}
