package rr.np.job.service;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import rr.np.job.controller.response.ErrorResponse;
import rr.np.job.controller.response.HotelSearchResponse;
import rr.np.job.controller.validator.ErrorCode;
import rr.np.job.repository.rest.AmadeusRestApiRepository;

@Service
public class HotelSearchService {

  private static final Logger log = LoggerFactory.getLogger(HotelSearchService.class);

  private static final int MAX_OUTPUT = 3;

  private final AmadeusRestApiRepository amadeusRestApiRepository;

  private ExecutorService restApiBackgroundService;

  @PostConstruct
  public void init() {
    this.restApiBackgroundService = Executors.newCachedThreadPool();
  }

  @Autowired
  public HotelSearchService(AmadeusRestApiRepository amadeusRestApiRepository) {
    this.amadeusRestApiRepository = amadeusRestApiRepository;
  }

  public DeferredResult<ResponseEntity<?>> searchHotels(@NotNull Map<String, String> searchCriteria, String requestEntry) {
    var callback = new DeferredResult<ResponseEntity<?>>();

    this.restApiBackgroundService.submit(() -> {
      try {
        var hotels = this.amadeusRestApiRepository.searchHotels(searchCriteria, requestEntry);
        log.debug("{} Completed request to Amadeus API. Number of Hotels found: {}", requestEntry, hotels.size());

        var response = hotels.stream()
                              .limit(MAX_OUTPUT)
                              .map((ho) -> {
                                var hotel = ho.getHotel();
                                var address = String.join(", ", asList(hotel.getAddress().getLines()))
                                    + ", " + hotel.getAddress().getCityName()
                                    + ", " + hotel.getAddress().getPostalCode();
                                var price = ho.getOffers()[0].getPrice();
                                var totalPrice = price.getTotal() != null ? price.getTotal() : price.getBase() + " " + price.getCurrency();

                                return new HotelSearchResponse(hotel.getName(), address, hotel.getContact().getPhone(), totalPrice);
                              })
                              .collect(toList());
        callback.setResult(new ResponseEntity<>(response, HttpStatus.OK));
        log.debug("{} HotelSearch request is completed successfully. Response: {}", requestEntry, callback.getResult());
      } catch (Exception e) {
        callback.setErrorResult(ErrorResponse.from(ErrorCode.ERR_101));
        log.debug("{} HotelSearch request is completed with errors. ErrorResponse: {}", requestEntry, e.getMessage());
      }
    });

    return callback;
  }

}
