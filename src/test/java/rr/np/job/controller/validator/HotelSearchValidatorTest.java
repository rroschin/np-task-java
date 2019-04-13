package rr.np.job.controller.validator;

import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
public class HotelSearchValidatorTest {

  static final String TEST = "unit-test";

  @Autowired
  HotelSearchValidator hotelSearchValidator;

  @Test
  public void validateSeachHotelsRequest_ValidRequest_ReturnNull() {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());

    var request = Map.of("cityCode", "PAR",
                         "checkInDate", today.format(format),
                         "checkOutDate", today.plusDays(1).format(format));
    // when
    var checkResult = this.hotelSearchValidator.validateSeachHotelsRequest(request, TEST);
    // then
    assertThat(checkResult).isNull();
  }

  @Test
  public void validateSeachHotelsRequest_InvalidRequest_ReturnErrorResponse() {
    // given
    var request = Map.of("cityCode", "123",
                         "checkInDate", "11-Apr-1999",
                         "checkOutDate", "");
    // when
    var checkResult = this.hotelSearchValidator.validateSeachHotelsRequest(request, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

}
