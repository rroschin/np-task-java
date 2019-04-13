package rr.np.job.controller.response;

import lombok.Value;

@Value
public class HotelSearchResponse {

  String hotelName;
  String hotelAddress;
  String hotelPhoneNumber;
  String roomRate;

}
