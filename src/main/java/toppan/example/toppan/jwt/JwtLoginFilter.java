package toppan.example.toppan.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

// also see - UsernamePasswordAuthenticationFilter

@Slf4j
public class JwtLoginFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/auth","POST");

private final ObjectMapper objectMapper ;


    protected JwtLoginFilter(ObjectMapper objectMapper) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper ;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        try {
            log.debug("Attempting authentication...");
            final var auth = objectMapper.readValue(request.getInputStream(), AuthDto.class);
            log.debug("Read user auth: {}", auth.username());

            final var authRequest = UsernamePasswordAuthenticationToken.unauthenticated(auth.username(), auth.password());

            authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));

            return this.getAuthenticationManager().authenticate(authRequest);
        } catch (final IOException ex) {
            throw new AuthenticationServiceException("Failed to parse authentication request body", ex);
        }
    }
}
