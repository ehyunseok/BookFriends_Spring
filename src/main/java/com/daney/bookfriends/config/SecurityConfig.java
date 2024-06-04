package com.daney.bookfriends.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/bookfriends", "/resources/**", "/css/**", "/js/**", "/user/login", "/user/register").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/bookfriends/user/login")
                                .defaultSuccessUrl("/bookfriends")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/bookfriends/logout")
                                .logoutSuccessUrl("/bookfriends/user/login")
                                .permitAll()
                );

        // csrf() 비활성화 (임시)
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
