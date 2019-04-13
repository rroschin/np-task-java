package rr.np.job.controller.validator;

import static org.assertj.core.api.Assertions.assertThat;

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
public class CityCodeValidatorTest {

  static final String TEST = "unit-test";

  @Autowired
  CityCodeValidator cityCodeValidator;

  @Test
  public void validateCityCode_ValidCityCode_ReturnNull() {
    // given
    var cityCode = "PAR";
    // when
    var checkResult = this.cityCodeValidator.validateCityCode(cityCode, TEST);
    // then
    assertThat(checkResult).isNull();
  }

  @Test
  public void validateCityCode_NullCityCode_ReturnErrorResponse() {
    // given
    String cityCode = null;
    // when
    var checkResult = this.cityCodeValidator.validateCityCode(cityCode, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCityCode_EmptyCityCode_ReturnErrorResponse() {
    // given
    String cityCode = "";
    // when
    var checkResult = this.cityCodeValidator.validateCityCode(cityCode, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCityCode_ShortCityCode_ReturnErrorResponse() {
    // given
    String cityCode = "PA";
    // when
    var checkResult = this.cityCodeValidator.validateCityCode(cityCode, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

  @Test
  public void validateCityCode_LongCityCode_ReturnErrorResponse() {
    // given
    String cityCode = "PARIS";
    // when
    var checkResult = this.cityCodeValidator.validateCityCode(cityCode, TEST);
    // then
    assertThat(checkResult).isNotNull();
    assertThat(checkResult.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(checkResult.getBody()).isNotNull();
  }

}
