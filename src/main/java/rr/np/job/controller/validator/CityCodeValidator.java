package rr.np.job.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import rr.np.job.controller.response.ErrorResponse;
import rr.np.job.controller.response.Response;

@Component
public class CityCodeValidator {

  private static final Logger log = LoggerFactory.getLogger(CityCodeValidator.class);

  public DeferredResult<ResponseEntity<? extends Response>> validateCityCode(String cityCode, String requestEntry) {
    var result = new DeferredResult<ResponseEntity<? extends Response>>();

    if (cityCode == null || cityCode.isBlank()) {
      var error = new ErrorResponse("ERR_001", "City Code cannot be empty.");
      var response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
      result.setErrorResult(response);

      log.debug("{} Validation for CityCode failed. CityCode is empty or null.", requestEntry);
      return result;
    }

    if (cityCode.trim().length() != 3) {
      var error = new ErrorResponse("ERR_002", "City Code must be 3 characters.");
      var response = new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
      result.setErrorResult(response);

      log.debug("{} Validation for CityCode failed. CityCode.lenght = {}.", requestEntry, cityCode.trim().length());
      return result;
    }

    return null;
  }

}
