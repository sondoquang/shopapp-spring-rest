package com.project.shopapp.configs;

import com.project.shopapp.filters.JwtTokenFilter;
import com.project.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;


    @Value("${api.prefix}")
    private String apiPrefix;


    // Filter chặn các request gửi den query data base //
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        requests ->{

                            requests
                                    .requestMatchers(
                                            String.format("%s/users/login", apiPrefix),
                                            String.format("%s/users/register", apiPrefix)
                                    )
                                    .permitAll()
                                    // CATEGORIES //
                                    .requestMatchers(GET, String.format("%s/categories/**", apiPrefix))
                                        .hasAnyRole(Role.ADMIN, Role.USER)
                                    .requestMatchers(POST, String.format("%s/categories/**", apiPrefix))
                                        .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/categories/**", apiPrefix))
                                        .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/categories/**", apiPrefix))
                                        .hasAnyRole(Role.ADMIN)

                                    // PRODUCTS //
                                    .requestMatchers(GET, String.format("%s/products/**", apiPrefix))
                                    .permitAll()

                                    .requestMatchers(POST, String.format("%s/products/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/products/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/products/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)

                                    // ORDER DETAIL //
                                    .requestMatchers(GET, String.format("%s/order_details/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN, Role.USER)
                                    .requestMatchers(POST, String.format("%s/order_details/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/order_details/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)
                                    .requestMatchers(PUT, String.format("%s/order_details/**", apiPrefix))
                                    .hasAnyRole(Role.ADMIN)

                                    .requestMatchers(POST,
                                            String.format("%s/orders/**", apiPrefix)).hasRole(Role.USER)
                                    .requestMatchers(PUT,
                                            String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)
                                    .requestMatchers(DELETE,
                                            String.format("%s/orders/**", apiPrefix)).hasRole(Role.ADMIN)
                                    .requestMatchers(GET,
                                            String.format("%s/orders/**", apiPrefix)).hasAnyRole(Role.ADMIN, Role.USER)
                                    .anyRequest().authenticated();
                        }
                );

         return http.build();
    }
}
