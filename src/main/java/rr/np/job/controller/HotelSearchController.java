package rr.np.job.controller;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import rr.np.job.controller.response.Response;
import rr.np.job.controller.validator.CityCodeValidator;
import rr.np.job.service.HotelSearchService;

@RestController
public class HotelSearchController {
  private static final Logger log = LoggerFactory.getLogger(HotelSearchController.class);

  private final CityCodeValidator cityCodeValidator;
  private final HotelSearchService hotelSearchService;

  @Autowired
  public HotelSearchController(final CityCodeValidator cityCodeValidator,
                               final HotelSearchService hotelSearchService) {
    this.cityCodeValidator = cityCodeValidator;
    this.hotelSearchService = hotelSearchService;
  }

  @GetMapping(value = "/hotels", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public DeferredResult<ResponseEntity<? extends Response>> searchHotels(@RequestParam("cityCode") String cityCode) {

    final var requestEntry = "<" + UUID.randomUUID() + ">";
    log.info("{} GET Request to /hotels. CityCode: {}", requestEntry, cityCode);

    final var checkResult = this.cityCodeValidator.validateCityCode(cityCode, requestEntry);
    if (checkResult != null) {
      log.warn("{} GET Request to /hotels is rejected. Validation Error.", requestEntry);
      return checkResult;
    }

    return this.hotelSearchService.searchHotelsByCityCode(cityCode, requestEntry);
  }
}
