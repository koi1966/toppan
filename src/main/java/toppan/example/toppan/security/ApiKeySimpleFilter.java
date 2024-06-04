package toppan.example.toppan.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.prefs.BackingStoreException;

@Slf4j
@Component
//@RequiredArgsConstructor
public class ApiKeySimpleFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain)
            throws ServletException, IOException {

        final String apiKey = request.getHeader("x-api-key");

        if (apiKey == null || apiKey.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if ("valid-key".equals(apiKey)) {
            log.debug("Valid API KEY provided");

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken("user",null,
                            List.of(new SimpleGrantedAuthority(ClientStatus.REGULAR.name())))
            );

            filterChain.doFilter(request, response);
        } else {
            log.debug("Invalid API KEY provided");
            throw new BadCredentialsException("Invalid API KEY");
        }
    }
}
