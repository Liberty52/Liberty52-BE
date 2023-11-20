package com.liberty52.auth.global.config;

import com.liberty52.auth.global.jwt.JwtAuthenticationProcessingFilter;
import com.liberty52.auth.global.jwt.JwtService;
import com.liberty52.auth.global.oauth2.CustomOAuth2UserService;
import com.liberty52.auth.global.oauth2.OAuth2LoginFailureHandler;
import com.liberty52.auth.global.oauth2.OAuth2LoginSuccessHandler;
import com.liberty52.auth.service.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtService jwtService;
    private final AuthRepository authRepository;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

//                .and()
//                .authorizeHttpRequests()
//                .anyRequest().permitAll()
//                .requestMatchers("/**").permitAll()

                .and()
                .oauth2Login()
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(oAuth2LoginFailureHandler)
                .userInfoEndpoint().userService(customOAuth2UserService);

        http.addFilterAfter(jwtAuthenticationProcessingFilter(), LogoutFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        JwtAuthenticationProcessingFilter jwtAuthenticationFilter = new JwtAuthenticationProcessingFilter(jwtService, authRepository);
        return jwtAuthenticationFilter;
    }
}
