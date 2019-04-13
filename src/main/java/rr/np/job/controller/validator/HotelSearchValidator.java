package rr.np.job.controller.validator;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchValidator {

  private final CityCodeValidator cityCodeValidator;
  private final CheckInCheckOutDateValidator checkInCheckOutDateValidator;

  @Autowired
  public HotelSearchValidator(CityCodeValidator cityCodeValidator, CheckInCheckOutDateValidator checkInCheckOutDateValidator) {
    this.cityCodeValidator = cityCodeValidator;
    this.checkInCheckOutDateValidator = checkInCheckOutDateValidator;
  }

  public ResponseEntity<?> validateSeachHotelsRequest(Map<String, String> request, String requestEntry) {

    var step1 = this.cityCodeValidator.validateCityCode(request.get("cityCode"), requestEntry);
    if (step1 != null) {
      return step1;
    }

    var step2 = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(request.get("checkInDate"), request.get("checkOutDate"), requestEntry);
    if (step2 != null) {
      return step2;
    }

    return null;
  }

}
