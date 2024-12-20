package com.skboard.security;

import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skboard.domain.Role;
import com.skboard.service.UserDetailService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final UserDetailService userDetailService;
	

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	
    	http.csrf((csrfConfig)->csrfConfig.disable())
    	.headers((headerConfig)->
    	headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
    	.authorizeHttpRequests((authorizeRequests)->
        authorizeRequests
        .requestMatchers(PathRequest.toH2Console()).permitAll()
        .requestMatchers("/", "/login/**").permitAll()
        .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
        .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
        .anyRequest().authenticated())
    	.formLogin((formLogin)->
    	formLogin.loginPage("/login")
    	//.usernameParameter("username")
    	.passwordParameter("password")
    	//.loginProcessingUrl("login/login-proc")
    	.defaultSuccessUrl("/",true))
    	.logout((logoutConfig) -> logoutConfig.logoutSuccessUrl("/"))
    	.userDetailsService(userDetailService)
    	.exceptionHandling((exceptionConfig) ->
    	exceptionConfig
    	.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler))
        .formLogin((formLogin) ->
                formLogin
                        .loginPage("/login/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login/login-proc")
                        .defaultSuccessUrl("/", true)
        )
        .logout((logoutConfig) ->
                logoutConfig.logoutSuccessUrl("/"))
        .userDetailsService(userDetailService);
    			
    	return http.build();
    }

    private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.UNAUTHORIZED, "Spring security unauthorized...");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {
                ErrorResponse fail = new ErrorResponse(HttpStatus.FORBIDDEN, "Spring security forbidden...");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

  	@Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}
