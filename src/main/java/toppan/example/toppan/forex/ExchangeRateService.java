package toppan.example.toppan.forex;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {

    private final ExchangeRateSimulator simulator;
    public ExchangeRate exchangeRate(final Currency from, final Currency to) {
        log.info("Exchange rate requested from {} to {}", from, to);
        return ExchangeRate.builder()
                .from(from)
                .to(to)
                .rate(simulator.getExchangeRate(from, to))
                .build();
    }
}
