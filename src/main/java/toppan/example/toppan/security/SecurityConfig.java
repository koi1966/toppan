package toppan.example.toppan.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties({ SecurityProperties.class })
public class SecurityConfig {

    private final ApiKeySimpleFilter simpleApiKeyAuthFilter;
    private final ApiKeyDbFilter apiKeyDbFilter;

    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(new AntPathRequestMatcher("/forex/currencies")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterAfter(simpleApiKeyAuthFilter, LogoutFilter.class)
                .addFilterAfter(apiKeyDbFilter, ApiKeySimpleFilter.class)
//                .addFilterAfter(jwtAuthenticationFilter, ApiKeyDbFilter.class)
//                .addFilterAfter(jwtLoginFilter, ApiKeyDbFilter.class)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
