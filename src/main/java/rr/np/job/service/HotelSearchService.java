package rr.np.job.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import rr.np.job.controller.response.ErrorResponse;
import rr.np.job.controller.response.Response;
import rr.np.job.repository.rest.AmadeusRestApiRepository;

@Service
public class HotelSearchService {
  private static final Logger log = LoggerFactory.getLogger(HotelSearchService.class);

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

  public DeferredResult<ResponseEntity<? extends Response>> searchHotelsByCityCode(String cityCode, String requestEntry) {
    var callback = new DeferredResult<ResponseEntity<? extends Response>>();

    this.restApiBackgroundService.submit(() -> {
      try {
        var hotelList = this.amadeusRestApiRepository.searchHotelsByCityCode(cityCode, requestEntry);
//        var hotelResponseList = mapper.map(hotelList); //TODO
        callback.setResult(new ResponseEntity<>(null));
      } catch (Exception e) {
        callback.setErrorResult(new ErrorResponse("ERR_101", e.getMessage()));
      }
    });

    return callback;
  }

}
