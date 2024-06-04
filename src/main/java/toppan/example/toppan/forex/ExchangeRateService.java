package toppan.example.toppan.forex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import toppan.example.toppan.security.ClientStatus;
import toppan.example.toppan.security.SecurityProperties;

import java.nio.channels.AcceptPendingException;
import java.nio.file.AccessDeniedException;
import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateSimulator simulator;

    public ExchangeRate exchangeRate(final Currency from, final Currency to) throws AccessDeniedException {

        log.info("Exchange rate requested from {} to {}", from, to);
        return ExchangeRate.builder()
                .from(from)
                .to(to)
                .rate(simulator.getExchangeRate(from, to))
                .build();
    }

}
