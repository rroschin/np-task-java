package rr.np.job.config;

import com.amadeus.Amadeus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Value("${amadeus.api.key}")
  private String amadeusApiKey;

  @Value("${amadeus.api.secret}")
  private String amadeusApiSecret;

  @Bean
  public Amadeus createAmadeus() {
    return Amadeus.builder(this.amadeusApiKey, this.amadeusApiSecret).build();
  }

}
