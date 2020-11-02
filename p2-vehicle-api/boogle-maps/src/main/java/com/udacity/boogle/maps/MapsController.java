package com.udacity.boogle.maps;

import org.springframework.web.bind.annotation.*;

@RestController
public class MapsController {

    @GetMapping("/maps/lat/{lat}/lng/{lng}")
    public Address get(
      @PathVariable("lat") Double lat,
      @PathVariable("lng") Double lng
    ) {
        Address address = MockAddressRepository.getRandom();
        return address;
    }
}
