package toppan.example.toppan.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.prefs.BackingStoreException;

@Slf4j
@Component
public class ApiKeySimpleFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {

        final String apiKey = request.getHeader("x-api-key");

        if ("valid-key".equals(apiKey)) {
            log.debug("Valid API KEY provided");

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken("user",null, List.of())
            );

            filterChain.doFilter(request, response);
        } else {
            log.debug("Invalid API KEY provided");
            throw new BadCredentialsException("Invalid API KEY");
        }
    }
}
