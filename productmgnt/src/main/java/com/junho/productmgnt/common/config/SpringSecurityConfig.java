package com.junho.productmgnt.common.config;

import com.junho.productmgnt.common.exception.AuthenticationEntryPointImpl;
import com.junho.productmgnt.common.filter.AuthenticationExceptionFilter;
import com.junho.productmgnt.common.filter.JwtAuthenticationFilter;
import com.junho.productmgnt.common.security.CookieAuthorizationRequestRepository;
import com.junho.productmgnt.common.security.CustomAccessDeniedHandler;
import com.junho.productmgnt.common.security.OAuth2AuthenticationFailureHandler;
import com.junho.productmgnt.common.security.OAuth2AuthenticationSuccessHandler;
import com.junho.productmgnt.common.util.JwtProvider;
import com.junho.productmgnt.domains.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {
    private final AuthenticationEntryPointImpl authenticationEntryPoint;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final AuthenticationExceptionFilter authenticationExceptionFilter;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(((authorizeRequests) ->
                authorizeRequests
                    .requestMatchers(HttpMethod.POST,"/api/products/**").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
                    .requestMatchers("/api/auth/me").authenticated()
                    .requestMatchers("/api/product-audits/**").hasRole("ADMIN")
                    .anyRequest().permitAll()
            ))
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            )
            .sessionManagement(
                sessionManagement ->
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(authenticationExceptionFilter, JwtAuthenticationFilter.class)
            .oauth2Login(oauth2Login ->
                oauth2Login
                    .authorizationEndpoint(authorizationEndpoint ->
                        authorizationEndpoint
                            .baseUri("/api/oauth2/authorize")
                            .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                    )
                    .redirectionEndpoint(redirectionEndpoint ->
                        redirectionEndpoint
                            .baseUri("/api/oauth2/callback/*"))
                    .userInfoEndpoint(userInfoEndpoint ->
                        userInfoEndpoint
                            .userService(customOAuth2UserService)
                    )
                    .successHandler(oAuth2AuthenticationSuccessHandler)
                    .failureHandler(oAuth2AuthenticationFailureHandler)
            )
            .logout(logout ->
                logout
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
            );
        return http.build();
    }
}