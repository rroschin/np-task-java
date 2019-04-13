package rr.np.job.controller.validator;

import static rr.np.job.controller.response.ErrorResponse.from;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import rr.np.job.controller.response.ErrorResponse;

@Component
public class CheckInCheckOutDateValidator {

  private static final Logger log = LoggerFactory.getLogger(CheckInCheckOutDateValidator.class);

  public ResponseEntity<ErrorResponse> validateCheckInCheckOutDates(String checkInDate, String checkOutDate, String requestEntry) {

    var dates = new SimpleDateFormat("yyyy-MM-dd");

    LocalDate checkInDateParsed;
    try {
      checkInDateParsed = dates.parse(checkInDate)
                               .toInstant()
                               .atZone(ZoneId.systemDefault())
                               .toLocalDate();
    } catch (Exception e) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_003), HttpStatus.BAD_REQUEST);
      log.debug("{} Validation for CheckInDate failed. Format of CheckInDate is wrong.", requestEntry);
      return response;
    }

    LocalDate checkOutDateParsed;
    try {
      checkOutDateParsed = dates.parse(checkOutDate)
                                .toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();
    } catch (Exception e) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_004), HttpStatus.BAD_REQUEST);
      log.debug("{} Validation for CheckInDate failed. Format of CheckOutDate is wrong.", requestEntry);
      return response;
    }

    LocalDate today = LocalDate.now(ZoneId.systemDefault());
    if (checkInDateParsed.isBefore(today)) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_005), HttpStatus.BAD_REQUEST);
      log.debug("{} Validation for CheckInDate failed. CheckInDate is in the past.", requestEntry);
      return response;
    }

    if (checkOutDateParsed.equals(checkInDateParsed) || checkOutDateParsed.isBefore(checkInDateParsed)) {
      var response = new ResponseEntity<>(from(ErrorCode.ERR_006), HttpStatus.BAD_REQUEST);
      log.debug("{} Validation for CheckInDate failed. CheckOutDate is before. CheckinDate", requestEntry);
      return response;
    }

    log.info("{} Validation for CheckInDate and CheckOutDate is finished. No validation errors.", requestEntry);
    return null;
  }

}
