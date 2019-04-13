package rr.np.job.controller;

import java.util.Map;
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
import rr.np.job.controller.validator.HotelSearchValidator;
import rr.np.job.service.HotelSearchService;

@RestController
public class HotelSearchController {
  private static final Logger log = LoggerFactory.getLogger(HotelSearchController.class);

  private final HotelSearchValidator hotelSearchValidator;
  private final HotelSearchService hotelSearchService;

  @Autowired
  public HotelSearchController(final HotelSearchValidator hotelSearchValidator,
                               final HotelSearchService hotelSearchService) {
    this.hotelSearchValidator = hotelSearchValidator;
    this.hotelSearchService = hotelSearchService;
  }

  @GetMapping(value = "/hotels", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public DeferredResult<ResponseEntity<?>>
  searchHotels(@RequestParam("cityCode") String cityCode,
               @RequestParam("checkInDate") String checkInDate,
               @RequestParam("checkOutDate") String checkOutDate) {

    final var requestEntry = "<" + UUID.randomUUID() + ">";
    log.info("{} GET Request to /hotels. CityCode: {}", requestEntry, cityCode);

    var request = Map.of("cityCode", cityCode,
                         "checkInDate", checkInDate,
                         "checkOutDate", checkOutDate);
    final var checkResult = this.hotelSearchValidator.validateSeachHotelsRequest(request, requestEntry);
    if (checkResult != null) {
      log.warn("{} GET Request to /hotels is rejected. Validation Error.", requestEntry);
      var result = new DeferredResult<ResponseEntity<?>>();
      result.setErrorResult(checkResult);
      return result;
    }

    return this.hotelSearchService.searchHotels(request, requestEntry);
  }

}
