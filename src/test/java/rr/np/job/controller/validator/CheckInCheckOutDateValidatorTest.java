package rr.np.job.controller.validator;

import static java.time.LocalDate.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
public class CheckInCheckOutDateValidatorTest {

  static final String TEST = "unit-test";

  @Autowired
  CheckInCheckOutDateValidator checkInCheckOutDateValidator;

  @Test
  public void validateCheckInCheckOutDates_ValidCheckInCheckOutDates_ReturnNull() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());
    var checkInDate = today.format(format);
    var checkOutDate = today.plusDays(1).format(format);
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNull();
  }

  @Test
  public void validateCheckInCheckOutDates_NullCheckInDate_ReturnErrorResponse() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());
    String checkInDate = null;
    var checkOutDate = today.plusDays(1).format(format);
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCheckInCheckOutDates_NullCheckOutDate_ReturnErrorResponse() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());
    var checkInDate = today.format(format);
    String checkOutDate = null;
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCheckInCheckOutDates_EmptyDates_ReturnErrorResponse() {
    // given
    var checkInDate = "";
    var checkOutDate = "";
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCheckInCheckOutDates_CheckInDateInPast_ReturnErrorResponse() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());
    var checkInDate = today.minusDays(2).format(format);
    var checkOutDate = today.plusDays(1).format(format);
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCheckInCheckOutDates_CheckOutDateLessThenCheckInDate_ReturnErrorResponse() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());
    var checkInDate = today.format(format);
    var checkOutDate = today.minusDays(1).format(format);
    // when
    var checkResult = this.checkInCheckOutDateValidator.validateCheckInCheckOutDates(checkInDate, checkOutDate, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }
}
