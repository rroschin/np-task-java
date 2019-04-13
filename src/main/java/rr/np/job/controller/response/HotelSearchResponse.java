package rr.np.job.controller.response;

import java.math.BigDecimal;
import lombok.Value;

@Value
public class HotelSearchResponse implements Response {

  String hotelName;
  String hotelAddress;
  String hotelPhoneNumber;
  BigDecimal roomRate;

}
