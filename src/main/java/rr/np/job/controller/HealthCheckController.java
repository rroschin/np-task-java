package rr.np.job.controller;

import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

  private static final Map<String, String> HEALTH_CHECK_RESPONSE = Map.of("health", "OK");

  @GetMapping(value = "/healthcheck", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public Map<String, String> applicationId() {

    return HEALTH_CHECK_RESPONSE;
  }
}
