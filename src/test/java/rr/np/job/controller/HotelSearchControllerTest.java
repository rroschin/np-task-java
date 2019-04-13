package rr.np.job.controller;

import static java.time.LocalDate.now;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.anyMap;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amadeus.resources.HotelOffer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rr.np.job.repository.rest.AmadeusRestApiRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("unit-test")
public class HotelSearchControllerTest {

  static final String URL = "/api/v1/hotels";

  @Autowired
  MockMvc mockMvc;
  @MockBean
  private AmadeusRestApiRepository amadeusRestApiRepository;

  @Before
  public void setup() throws Exception {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    var hotel1Json = "{\"type\":\"hotel-offers\",\"hotel\":{\"type\":\"hotel\",\"hotelId\":\"EAMIAMAP\",\"chainCode\":\"EA\",\"dupeId\":\"700107767\",\"name\":\"CONCORDE OPERA PARIS OPERA PARIS\",\"rating\":\"2\",\"cityCode\":\"PAR\",\"latitude\":48.85693,\"longitude\":2.3412,\"hotelDistance\":{\"distance\":0.4,\"distanceUnit\":\"KM\"},\"address\":{\"lines\":[\"LEFISTON STREET\"],\"cityName\":\"PARIS\",\"countryCode\":\"FR\"},\"contact\":{\"phone\":\"1-305-436-1811\",\"fax\":\"1-305-436-1864\"},\"amenities\":[\"HANDICAP_FAC\",\"ACC_BATHS\",\"ACC_WASHBASIN\",\"ACC_BATH_CTRLS\",\"ACC_LIGHT_SW\",\"ACC_ELEVATORS\",\"ACC_TOILETS\",\"SERV_DOGS_ALWD\",\"DIS_PARKG\",\"HANDRAIL_BTHRM\",\"ADAPTED_PHONES\",\"ADAPT_RM_DOORS\",\"ACC_RM_WCHAIR\",\"TV_SUB/CAPTION\",\"ACC_WCHAIR\",\"HANDRAIL_BTHRM\",\"EXT_ROOM_ENTRY\",\"EMERG_LIGHTING\",\"EXTINGUISHERS\",\"FIRE_SAFETY\",\"RESTRIC_RM_ACC\",\"SMOKE_DETECTOR\",\"SPRINKLERS\",\"KIDS_WELCOME\",\"ELEVATOR\",\"INT_HOTSPOTS\",\"FREE_INTERNET\",\"LAUNDRY_SVC\",\"NO_PORN_FILMS\",\"PARKING\",\"PETS_ALLOWED\",\"SWIMMING_POOL\",\"AIR_CONDITIONING\",\"KITCHEN\",\"NONSMOKING_RMS\",\"TELEVISION\",\"WI-FI_IN_ROOM\"],\"media\":[{\"uri\":\"http://pdt.multimediarepository.testing.amadeus.com/cmr/retrieve/hotel/69810B23CB8644A18AF760DC66BE41A6\",\"category\":\"EXTERIOR\"}]},\"available\":true,\"offers\":[{\"id\":\"ECF8AD653801C3A81B3D04BAEF0D85AF182E8FB2115B0FAD144D49FAD108C60D\",\"rateCode\":\"RAC\",\"room\":{\"type\":\"A0C\",\"typeEstimated\":{\"category\":\"ACCESSIBLE_ROOM\",\"beds\":1,\"bedType\":\"QUEEN\"},\"description\":{\"lang\":\"EN\",\"text\":\"NIGHTLY VALUE RATE\\nSTANDARD STUDIO 1 QUEEN NONSMKNG ACCESSIBLE\\nFREE WIFI\"}},\"guests\":{\"adults\":1},\"price\":{\"currency\":\"USD\",\"base\":\"114.99\",\"total\":\"129.94\",\"variations\":{\"average\":{\"base\":\"114.99\"},\"changes\":[{\"startDate\":\"2019-01-22\",\"endDate\":\"2019-01-23\",\"base\":\"114.99\"}]}}}]}";
    var hotel1 = gson.fromJson(hotel1Json, HotelOffer.class);
    var hotel2Json = "{\"type\":\"hotel-offers\",\"hotel\":{\"type\":\"hotel\",\"hotelId\":\"BWPAR261\",\"chainCode\":\"BW\",\"dupeId\":\"700019804\",\"name\":\"BEST WESTERN AU TROCADERO\",\"rating\":\"3\",\"cityCode\":\"PAR\",\"latitude\":48.85326,\"longitude\":2.33855,\"hotelDistance\":{\"distance\":0.5,\"distanceUnit\":\"KM\"},\"address\":{\"lines\":[\"3 AVENUE RAYMOND-POINCARE \"],\"postalCode\":\"FR75116\",\"cityName\":\"PARIS\",\"countryCode\":\"FR\"},\"contact\":{\"fax\":\"33-1-47278085\",\"phone\":\"33-1-47273330\"},\"description\":{\"lang\":\"en\",\"text\":\"The best western au trocadero is located in one of the most elegant district of paris, facing the trocadero, the eiffel tower and champ de mars. You will enjoy the friendly setting.\"},\"amenities\":[\"BAR\",\"ICE_MACHINES\",\"RESTAURANT\",\"ACC_BATHS\",\"ACC_TOILETS\",\"HANDRAIL_BTHRM\",\"ACC_RM_WCHAIR\",\"BABY-SITTING\",\"KIDS_WELCOME\",\"ELEVATOR\",\"WIFI\",\"LAUNDRY_SVC\",\"LOUNGE\",\"AIR_CONDITIONING\",\"HAIR_DRYER\",\"MINIBAR\",\"NONSMOKING_RMS\",\"ROOM_SERVICE\",\"TELEVISION\",\"WI-FI_IN_ROOM\",\"EXT_ROOM_ENTRY\",\"EMERG_LIGHTING\",\"EXTINGUISHERS\",\"FIRE_SAFETY\",\"SMOKE_DETECTOR\",\"VIDEO_SURVEIL\"],\"media\":[{\"uri\":\"http://pdt.multimediarepository.testing.amadeus.com/cmr/retrieve/hotel/D231E485EDCB4AB1B61F3032404474D1\",\"category\":\"EXTERIOR\"}]},\"available\":true,\"offers\":[{\"id\":\"7D158B52F8A4EED40A30AA8D96E957EC26C3AC381099486510BA1E32F468FA35\",\"rateCode\":\"RAC\",\"rateFamilyEstimated\":{\"code\":\"BAR\",\"type\":\"P\"},\"room\":{\"type\":\"A2T\",\"description\":{\"lang\":\"EN\",\"text\":\"FLEXIBLE RATE*BEST LEAST RESTRICTIVE RATE\\n2 SINGLE BEDS,CONFORT RM,GRABBR,RAMP ACCESS,\\nWHEELCHAIR\"}},\"guests\":{\"adults\":1},\"price\":{\"currency\":\"EUR\",\"base\":\"150.00\",\"total\":\"151.65\",\"variations\":{\"average\":{\"base\":\"150.00\"},\"changes\":[{\"startDate\":\"2019-01-22\",\"endDate\":\"2019-01-23\",\"base\":\"150.00\"}]}}}]}";
    var hotel2 = gson.fromJson(hotel2Json, HotelOffer.class);
    var hotel3Json = "{\"type\":\"hotel-offers\",\"hotel\":{\"type\":\"hotel\",\"hotelId\":\"EAMIAMAP\",\"chainCode\":\"EA\",\"dupeId\":\"700107768\",\"name\":\"CONCORDE OPERA PARIS OPERA PARIS 2\",\"rating\":\"2\",\"cityCode\":\"PAR\",\"latitude\":48.85693,\"longitude\":2.3412,\"hotelDistance\":{\"distance\":0.4,\"distanceUnit\":\"KM\"},\"address\":{\"lines\":[\"LEFISTON STREET 2\"],\"cityName\":\"PARIS\",\"countryCode\":\"FR\"},\"contact\":{\"phone\":\"1-305-436-1811\",\"fax\":\"1-305-436-1864\"},\"amenities\":[\"HANDICAP_FAC\",\"ACC_BATHS\",\"ACC_WASHBASIN\",\"ACC_BATH_CTRLS\",\"ACC_LIGHT_SW\",\"ACC_ELEVATORS\",\"ACC_TOILETS\",\"SERV_DOGS_ALWD\",\"DIS_PARKG\",\"HANDRAIL_BTHRM\",\"ADAPTED_PHONES\",\"ADAPT_RM_DOORS\",\"ACC_RM_WCHAIR\",\"TV_SUB/CAPTION\",\"ACC_WCHAIR\",\"HANDRAIL_BTHRM\",\"EXT_ROOM_ENTRY\",\"EMERG_LIGHTING\",\"EXTINGUISHERS\",\"FIRE_SAFETY\",\"RESTRIC_RM_ACC\",\"SMOKE_DETECTOR\",\"SPRINKLERS\",\"KIDS_WELCOME\",\"ELEVATOR\",\"INT_HOTSPOTS\",\"FREE_INTERNET\",\"LAUNDRY_SVC\",\"NO_PORN_FILMS\",\"PARKING\",\"PETS_ALLOWED\",\"SWIMMING_POOL\",\"AIR_CONDITIONING\",\"KITCHEN\",\"NONSMOKING_RMS\",\"TELEVISION\",\"WI-FI_IN_ROOM\"],\"media\":[{\"uri\":\"http://pdt.multimediarepository.testing.amadeus.com/cmr/retrieve/hotel/69810B23CB8644A18AF760DC66BE41A6\",\"category\":\"EXTERIOR\"}]},\"available\":true,\"offers\":[{\"id\":\"ECF8AD653801C3A81B3D04BAEF0D85AF182E8FB2115B0FAD144D49FAD108C60D\",\"rateCode\":\"RAC\",\"room\":{\"type\":\"A0C\",\"typeEstimated\":{\"category\":\"ACCESSIBLE_ROOM\",\"beds\":1,\"bedType\":\"QUEEN\"},\"description\":{\"lang\":\"EN\",\"text\":\"NIGHTLY VALUE RATE\\nSTANDARD STUDIO 1 QUEEN NONSMKNG ACCESSIBLE\\nFREE WIFI\"}},\"guests\":{\"adults\":1},\"price\":{\"currency\":\"USD\",\"base\":\"179.99\",\"total\":\"189.94\",\"variations\":{\"average\":{\"base\":\"179.99\"},\"changes\":[{\"startDate\":\"2019-01-22\",\"endDate\":\"2019-01-23\",\"base\":\"179.99\"}]}}}]}";
    var hotel3 = gson.fromJson(hotel3Json, HotelOffer.class);
    var hotel4Json = "{\"type\":\"hotel-offers\",\"hotel\":{\"type\":\"hotel\",\"hotelId\":\"EAMIAMAP\",\"chainCode\":\"EA\",\"dupeId\":\"700107768\",\"name\":\"CONCORDE OPERA PARIS OPERA PARIS 3\",\"rating\":\"2\",\"cityCode\":\"PAR\",\"latitude\":48.85693,\"longitude\":2.3412,\"hotelDistance\":{\"distance\":0.4,\"distanceUnit\":\"KM\"},\"address\":{\"lines\":[\"LEFISTON STREET 3\"],\"cityName\":\"PARIS\",\"countryCode\":\"FR\"},\"contact\":{\"phone\":\"1-305-436-1811\",\"fax\":\"1-305-436-1864\"},\"amenities\":[\"HANDICAP_FAC\",\"ACC_BATHS\",\"ACC_WASHBASIN\",\"ACC_BATH_CTRLS\",\"ACC_LIGHT_SW\",\"ACC_ELEVATORS\",\"ACC_TOILETS\",\"SERV_DOGS_ALWD\",\"DIS_PARKG\",\"HANDRAIL_BTHRM\",\"ADAPTED_PHONES\",\"ADAPT_RM_DOORS\",\"ACC_RM_WCHAIR\",\"TV_SUB/CAPTION\",\"ACC_WCHAIR\",\"HANDRAIL_BTHRM\",\"EXT_ROOM_ENTRY\",\"EMERG_LIGHTING\",\"EXTINGUISHERS\",\"FIRE_SAFETY\",\"RESTRIC_RM_ACC\",\"SMOKE_DETECTOR\",\"SPRINKLERS\",\"KIDS_WELCOME\",\"ELEVATOR\",\"INT_HOTSPOTS\",\"FREE_INTERNET\",\"LAUNDRY_SVC\",\"NO_PORN_FILMS\",\"PARKING\",\"PETS_ALLOWED\",\"SWIMMING_POOL\",\"AIR_CONDITIONING\",\"KITCHEN\",\"NONSMOKING_RMS\",\"TELEVISION\",\"WI-FI_IN_ROOM\"],\"media\":[{\"uri\":\"http://pdt.multimediarepository.testing.amadeus.com/cmr/retrieve/hotel/69810B23CB8644A18AF760DC66BE41A6\",\"category\":\"EXTERIOR\"}]},\"available\":true,\"offers\":[{\"id\":\"ECF8AD653801C3A81B3D04BAEF0D85AF182E8FB2115B0FAD144D49FAD108C60D\",\"rateCode\":\"RAC\",\"room\":{\"type\":\"A0C\",\"typeEstimated\":{\"category\":\"ACCESSIBLE_ROOM\",\"beds\":1,\"bedType\":\"QUEEN\"},\"description\":{\"lang\":\"EN\",\"text\":\"NIGHTLY VALUE RATE\\nSTANDARD STUDIO 1 QUEEN NONSMKNG ACCESSIBLE\\nFREE WIFI\"}},\"guests\":{\"adults\":1},\"price\":{\"currency\":\"USD\",\"base\":\"229.99\",\"total\":\"259.94\",\"variations\":{\"average\":{\"base\":\"229.99\"},\"changes\":[{\"startDate\":\"2019-01-22\",\"endDate\":\"2019-01-23\",\"base\":\"229.99\"}]}}}]}";
    var hotel4 = gson.fromJson(hotel4Json, HotelOffer.class);

    var hotels = List.of(hotel1, hotel2, hotel3, hotel4);
    given(this.amadeusRestApiRepository.searchHotels(anyMap(), anyString())).willReturn(hotels);
  }

  @Test
  public void searchHotels_ValidRequest_Return3HotelsJson() throws Exception {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());

    var cityCode = "PAR";
    var checkInDate = today.format(format);
    var checkOutDate = today.plusDays(1).format(format);

    // when
    final var actualMvcResult = this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                                             .param("cityCode", cityCode)
                                                             .param("checkInDate", checkInDate)
                                                             .param("checkOutDate", checkOutDate))
                                            .andReturn();

    // then
    var expectedJson = "[{\"hotelName\":\"CONCORDE OPERA PARIS OPERA PARIS\",\"hotelAddress\":\"LEFISTON STREET, PARIS, null\",\"hotelPhoneNumber\":\"1-305-436-1811\",\"roomRate\":\"129.94\"},{\"hotelName\":\"BEST WESTERN AU TROCADERO\",\"hotelAddress\":\"3 AVENUE RAYMOND-POINCARE , PARIS, FR75116\",\"hotelPhoneNumber\":\"33-1-47273330\",\"roomRate\":\"151.65\"},{\"hotelName\":\"CONCORDE OPERA PARIS OPERA PARIS 2\",\"hotelAddress\":\"LEFISTON STREET 2, PARIS, null\",\"hotelPhoneNumber\":\"1-305-436-1811\",\"roomRate\":\"189.94\"}]";
    this.mockMvc.perform(asyncDispatch(actualMvcResult)).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(expectedJson));
  }

  @Test
  public void searchHotels_InvalidRequest_ReturnErrorJson() throws Exception {
    // given
    var format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    var today = now(ZoneId.systemDefault());

    var cityCode = "PAR";
    var checkInDate = "BadDate";
    var checkOutDate = today.plusDays(1).format(format);

    // when
    final var actualMvcResult = this.mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                                                             .param("cityCode", cityCode)
                                                             .param("checkInDate", checkInDate)
                                                             .param("checkOutDate", checkOutDate))
                                            .andReturn();

    // then
    var expectedJson = "{\"errorCode\":\"ERR_003\",\"errorMessage\":\"Check-In Date wrong format.\"}";
    this.mockMvc.perform(asyncDispatch(actualMvcResult)).andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(content().json(expectedJson));
  }

}
