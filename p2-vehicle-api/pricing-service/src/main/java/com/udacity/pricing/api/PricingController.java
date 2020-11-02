package com.udacity.pricing.api;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements a REST-based controller for the pricing service.
 */
@RestController
@RequestMapping("/services/price")
public class PricingController {
  @Autowired
  PricingService pricingService;
    /**
     * Gets the price for a requested vehicle.
     * @param vehicleId ID number of the vehicle for which the price is requested
     * @return price of the vehicle, or error that it was not found.
     */
    @GetMapping("{vehicleId}")
    public Price get(
      @PathVariable("vehicleId") Long vehicleId
    ) {
        try {
            Price carPrice = pricingService.getPrice(vehicleId);
            return carPrice;
        } catch (PriceException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Price Not Found", ex);
        }
    }
}
