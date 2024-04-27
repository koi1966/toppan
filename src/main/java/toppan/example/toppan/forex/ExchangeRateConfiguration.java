package toppan.example.toppan.forex;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ExchangeRateProperties.class)
class ExchangeRateConfiguration {

}
