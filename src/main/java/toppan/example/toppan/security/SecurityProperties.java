package toppan.example.toppan.security;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import toppan.example.toppan.forex.Currency;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Service
@ConfigurationProperties(prefix = "app.security")
public class SecurityProperties {

    private Map<ClientStatus, String> statusCurrencies;

    public Map<ClientStatus, Set<Currency>> getStatusCurrencies() {
        return statusCurrencies.entrySet().stream()
                .map(entry -> Map.entry(entry.getKey(), Arrays.stream(entry.getValue().split(","))
                        .map(String::strip)
                        .map(Currency::valueOf)
                        .collect(Collectors.toUnmodifiableSet())))
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
