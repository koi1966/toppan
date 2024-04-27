package toppan.example.toppan.forex;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forex")
@RequiredArgsConstructor
public class ExchangeRateController {

  public final ExchangeRateService exchangeRateService;
    @GetMapping
//    @PreAuthorize("@statusCurrencySecurityEvaluator.hasAccessToCurrencies(authentication, #from, #to)")
    public ResponseEntity<ExchangeRate> exchangeRate(
            final Currency from,
            final Currency to
    ) {

        return ResponseEntity.ok(exchangeRateService.exchangeRate(from, to));
    }
}
