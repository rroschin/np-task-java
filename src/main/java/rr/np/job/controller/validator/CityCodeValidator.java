package rr.np.job.controller.validator;

import static rr.np.job.controller.response.ErrorResponse.from;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rr.np.job.controller.response.ErrorResponse;

@Component
public class CityCodeValidator {

  private static final Logger log = LoggerFactory.getLogger(HotelSearchValidator.class);

  public ResponseEntity<ErrorResponse> validateCityCode(String cityCode, String requestEntry) {

    if (cityCode == null || cityCode.isBlank()) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_001), HttpStatus.BAD_REQUEST);
      log.debug("{} Validation for CityCode failed. CityCode is empty or null.", requestEntry);
      return response;
    }

    if (cityCode.trim().length() != 3) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_002), HttpStatus.BAD_REQUEST);

      log.debug("{} Validation for CityCode failed. CityCode.lenght = {}.", requestEntry, cityCode.trim().length());
      return response;
    }

    log.info("{} Validation for CityCode is finished. No validation errors.", requestEntry);
    return null;
  }

}
