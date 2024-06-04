package toppan.example.toppan.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import toppan.example.toppan.forex.Currency;

@Component
@RequiredArgsConstructor
public class StatusCurrencySecurityEvaluator {

    private final SecurityProperties securityProperties;

    public boolean hasAccessToCurrencies(final Authentication authentication, final Currency from, final Currency to) {
        return authentication.getAuthorities().stream()
                .flatMap(authority -> ClientStatus.fromString(authority.getAuthority()).stream())
                .map(securityProperties.getStatusCurrencies()::get)
                .anyMatch(currencies -> currencies.contains(from) && currencies.contains(to));
    }

}
