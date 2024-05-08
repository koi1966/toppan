package toppan.example.toppan.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ApiKeySimpleFilter simpleApiKeyAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();
        http.authorizeRequests().anyRequest().authenticated().and().httpBasic();
        http.addFilterAfter(simpleApiKeyAuthFilter, LogoutFilter.class);
        return http.build();
    }
}
