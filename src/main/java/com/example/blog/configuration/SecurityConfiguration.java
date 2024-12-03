package com.example.blog.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
//                    .securityMatchers((matchers) -> matchers
//                            .requestMatchers("/api/**"))
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers(
                                        "/auth/authenticate",
                                        "/home",
                                        "/login",
                                        "/userBlogList",
                                        "/about-us",
                                        "/contact-us",
                                        "/faq",
                                        "/log-out",
                                        "/create-faq",
                                        "/manage-faq",
                                        "/user-profile",
                                        "/auth/registration",
                                        "/auth/refreshToken",
                                        "/auth/register/management",
                                        "/auth/reset-password",
                                        "/auth/provide-reset-password",
                                        "/auth/regenerate/token-for-user",
                                        "/auth/confirm-account",
                                        "/error",
                                        "*.css",
                                        "*.js",
                                        "*.jpeg",
                                        "*.jpg")
                                .permitAll()
                                .requestMatchers("/blog-docs/**","blog-ui","swagger-ui/**")
                                .permitAll()
                                .requestMatchers("/faq/get-list","/api/video-content/get-all")
                                .permitAll()
                                //.requestMatchers(new AntPathRequestMatcher("/error"))
                                //.permitAll()
                                .anyRequest()
                                .authenticated())

                .sessionManagement(sessionManagement->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
