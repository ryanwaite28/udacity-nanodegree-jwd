package com.udacity.pricing;

import com.udacity.pricing.api.PricingController;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PricingController.class)
public class PricingControllerTests {
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private PricingService pricingService;
  
  @AfterEach
  public void validate() {
    validateMockitoUsage();
  }
  
  @Test
  public void failsOnInvalidVehicleId() throws Exception {
    mockMvc.perform(get("/services/price/0"))
      .andExpect(status().isNotFound());
  }
  
  @Test
  public void passOnValidVehicleId() throws Exception {
    mockMvc.perform(get("/services/price/1"))
      .andExpect(status().isOk());
  }
}
