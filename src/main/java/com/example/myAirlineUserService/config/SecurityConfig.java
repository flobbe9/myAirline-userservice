package com.example.myAirlineUserService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


// TODO: authenticate paths properly
/**
 * Configuration class to authentiacate requests.
 * 
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http
            .csrf().disable()
			.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/user/getByEmail").hasRole("USER")
				.anyRequest()
                .permitAll()
            )
            .formLogin()
                .disable()
            .logout()
                .disable();
                // .logoutUrl("/logout")
                // .deleteCookies("JSESSIONID");

        return http.build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        
        return new BCryptPasswordEncoder(10);
    }
}