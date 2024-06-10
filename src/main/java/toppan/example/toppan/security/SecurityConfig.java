package toppan.example.toppan.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import toppan.example.toppan.jwt.JwtAuthenticationFilter;
import toppan.example.toppan.jwt.JwtLoginFilter;
import toppan.example.toppan.jwt.JwtProperties;
import toppan.example.toppan.jwt.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties({SecurityProperties.class, JwtProperties.class})
public class SecurityConfig {

    private final ApiKeySimpleFilter simpleApiKeyAuthFilter;
    private final ApiKeyDbFilter apiKeyDbFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(final HttpSecurity http, final JwtLoginFilter jwtLoginFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                       .requestMatchers(new AntPathRequestMatcher("/forex/currencies")).permitAll()
                       .requestMatchers(new AntPathRequestMatcher("/karta/**")).permitAll()
//                     .requestMatchers(new AntPathRequestMatcher("/**")).permitAll()
                       .requestMatchers(new AntPathRequestMatcher("/auth")).permitAll()
                       .anyRequest().authenticated()
                )
                .addFilterAfter(simpleApiKeyAuthFilter, LogoutFilter.class)
                .addFilterAfter(apiKeyDbFilter, ApiKeySimpleFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, ApiKeyDbFilter.class)
                .addFilterAfter(jwtLoginFilter, JwtAuthenticationFilter.class)

                .oauth2Login(Customizer.withDefaults())
//
// Steitleas
//                .exceptionHandling((exceptionHandling) -> exceptionHandling
//                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//                )
//                .formLogin(Customizer.withDefaults());
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

//                .addFilterAfter(jwtAuthenticationFilter, ApiKeyDbFilter.class)
//                .addFilterAfter(jwtLoginFilter, ApiKeyDbFilter.class)

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtLoginFilter jwtLoginFilter(
            final ObjectMapper objectMapper,
            final AuthenticationConfiguration configuration,
            final JwtService jwtService
    ) throws Exception {
        final JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(objectMapper, jwtService);
        jwtLoginFilter.setAuthenticationManager(configuration.getAuthenticationManager());
        return jwtLoginFilter;
    }
}
