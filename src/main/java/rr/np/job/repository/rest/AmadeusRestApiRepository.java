package rr.np.job.repository.rest;

import com.amadeus.Amadeus;
import com.amadeus.Params;
import com.amadeus.resources.HotelOffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AmadeusRestApiRepository {

  private static final Logger log = LoggerFactory.getLogger(AmadeusRestApiRepository.class);

  private final Amadeus amadeus;

  @Autowired
  public AmadeusRestApiRepository(Amadeus amadeus) {
    this.amadeus = amadeus;
  }

  public List<HotelOffer> searchHotels(@NotNull Map<String, String> searchCriteria, String requestEntry) throws Exception {

    var params = Params.with("cityCode", searchCriteria.get("cityCode"))
                       .and("checkInDate", searchCriteria.get("checkInDate"))
                       .and("checkOutDate", searchCriteria.get("checkOutDate"))
                       .and("sort", "PRICE");
    log.debug("{} Request parameters for Amadeus HotelSearch request: {}", requestEntry, params);

    var offers = Arrays.asList(amadeus.shopping.hotelOffers.get(params));
    log.debug("{} Request to Amadeus HotelSearch API is completed. Response size: {}", requestEntry, offers.size());

    if (offers.get(0).getResponse().getStatusCode() != 200) {
      log.debug("{} Request to Amadeus HotelSearch API returned an error. StatusCode: {}", requestEntry, offers.get(0).getResponse().getStatusCode());
      throw new RuntimeException("Amadeus API Error Response: " + offers.get(0).getResponse().getStatusCode());
    }

    return offers;
  }
}
