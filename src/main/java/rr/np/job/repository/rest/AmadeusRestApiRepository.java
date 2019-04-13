package rr.np.job.repository.rest;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.exceptions.ResponseException;
import com.amadeus.resources.HotelOffer;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;
import rr.np.job.controller.response.HotelSearchResponse;
import rr.np.job.repository.domain.AmadeusApiHotel;

@Component
public class AmadeusRestApiRepository {
  private static final Logger log = LoggerFactory.getLogger(AmadeusRestApiRepository.class);

  private final Amadeus amadeus;

  @Autowired
  public AmadeusRestApiRepository(Amadeus amadeus) {
    this.amadeus = amadeus;
  }

  @Async
  public List<AmadeusApiHotel> searchHotelsByCityCode(String cityCode, String requestEntry) throws Exception {

    /* 1st endpoint - What are the best hotel offers during my trip to Madrid? */
    var offers = Arrays.asList(amadeus.shopping.hotelOffers.get(Params.with("cityCode", cityCode)));
    if (offers.get(0).getResponse().getStatusCode() != 200) {
      throw new RuntimeException("Amadeus API Error Response: " + offers.get(0).getResponse().getStatusCode());
    }

    /* 2nd endpoint - Select one hotel and query it for offers (you might have to change the hotel_id) */
    HotelOffer hotelOffer = amadeus.shopping.hotelOffersByHotel.get(Params.with("hotelId", "BGLONBGB"));
    if (hotelOffer.getResponse().getStatusCode() != 200) {
      System.out.println("Wrong status code for Hotel 2nd endpoint: " + hotelOffer.getResponse().getStatusCode());
      System.exit(-1);
    }
    System.out.println(hotelOffer);

    /* 3rd endpoint - Availability call for a specific hotel/offer (you might have to update the hotel_id and the offer_id */
    HotelOffer offer = amadeus.shopping.hotelOffer("4BA070CE929E135B3268A9F2D0C51E9D4A6CF318BA10485322FA2C7E78C7852E").get();
    if (offer.getResponse().getStatusCode() != 200) {
      System.out.println("Wrong status code for Hotel 3rd endpoint: " + offer.getResponse().getStatusCode());
      System.exit(-1);
    }
    System.out.println(offer);

    return null;
  }
}
