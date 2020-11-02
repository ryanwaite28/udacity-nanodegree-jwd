package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PricingServiceApplicationTests {
  
  @Autowired
  private PricingService pricingService;

	@Test
	public void contextLoads() {
  }
  
  @Test
  public void throwsErrorForInvalidVehicleId() {
	  // lower bound
	  try {
	    // pass invalid id number
	    Price price = pricingService.getPrice(0L);
	    // if service doesn't throw exception, then something is wrong; fail the test
      Assertions.fail("Pricing Service did not fail when passing an invalid vehicle id");
    } catch (PriceException priceException) {
      // expected error happened; pass
    }
  
	  // upper bound
    try {
      // pass invalid id number
      Price price = pricingService.getPrice(21L);
      // if service doesn't throw exception, then something is wrong; fail the test
      Assertions.fail("Pricing Service did not fail when passing an invalid vehicle id");
    } catch (PriceException priceException) {
      // expected error happened; pass
      return;
    }
  }
  
  @Test
  public void successfullyGetsPriceByValidVehicleId() {
    // lower bound
    try {
      // pass valid id number
      Price price = pricingService.getPrice(1L);
    } catch (PriceException priceException) {
      // if service does throw exception, then something is wrong; fail the test
      Assertions.fail("Pricing Service did not pass when passing a valid vehicle id: 1L");
    }
    
    // upper bound
    try {
      // pass valid id number
      Price price = pricingService.getPrice(19L);
    } catch (PriceException priceException) {
      // if service does throw exception, then something is wrong; fail the test
      Assertions.fail("Pricing Service did not pass when passing a valid vehicle id: 19L");
    }
  }
}
