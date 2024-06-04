package toppan.example.toppan.forex;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Component
@RestController
@RequestMapping("/forex")
@RequiredArgsConstructor
public class ExchangeRateController {

    public final ExchangeRateService exchangeRateService;

    @GetMapping
    @PreAuthorize("@statusCurrencySecurityEvaluator.hasAccessToCurrencies(authentication, #from, #to)")
    public ResponseEntity<ExchangeRate> exchangeRate(
            final Currency from,
            final Currency to
    ) throws AccessDeniedException {
        return ResponseEntity.ok(exchangeRateService.exchangeRate(from, to));
    }

    @GetMapping("/currencies")
    public ResponseEntity<List<Currency>> allCurrencies() {
        return ResponseEntity.ok(List.of(Currency.values()));
    }

}
