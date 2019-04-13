package rr.np.job;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import rr.np.job.controller.HealthCheckController;
import rr.np.job.controller.HotelSearchController;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("unit-test")
public class NpJobTaskStarterTest {

  @Autowired
  HealthCheckController healthCheckController;
  @Autowired
  HotelSearchController hotelSearchController;

  @Test
  public void contexLoads_StartingApplication_ControllersNotNull() {

    assertThat(this.healthCheckController).isNotNull();
    assertThat(this.hotelSearchController).isNotNull();
  }

}
