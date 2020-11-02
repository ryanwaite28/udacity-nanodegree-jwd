package com.udacity.vehicles.service;


import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the car service create, read, update or delete
 * information about vehicles, as well as gather related
 * location and price data when desired.
 */
@Service
public class CarService {
  
  @Autowired
  private CarRepository carRepository;
  
  @Autowired
  private PriceClient priceClient;
  
  @Autowired
  private MapsClient mapsClient;
  
  /**
   * Gathers a list of all vehicles
   * @return a list of all vehicles in the CarRepository
   */
  public List<Car> list() {
    List<Car> carsList = carRepository.findAll();
    return carsList;
  }
  
  /**
   * Gets car information by ID (or throws exception if non-existent)
   * @param carId the ID number of the car to gather information on
   * @return the requested car's information, including location and price
   */
  public Car findById(Long carId) {
    /* TODO: Find the car by ID from the `repository` if it exists.
     *   If it does not exist, throw a CarNotFoundException
     *   Remove the below code as part of your implementation. */
    Optional<Car> carQuery = carRepository.findById(carId);
    if (!carQuery.isPresent()) {
      throw new CarNotFoundException("Car not found by id: " + carId);
    }
    Car car = carQuery.get();
    
    /* TODO: Use the Pricing Web client you create in `VehiclesApiApplication`
     *   to get the price based on the `id` input' */
    String carPrice = priceClient.getPrice(carId);
    
    /* TODO: Set the price of the car
     * Note: The car class file uses @transient, meaning you will need to call
     *   the pricing service each time to get the price.
     */
    car.setPrice(carPrice);
    
    
    /* TODO: Use the Maps Web client you create in `VehiclesApiApplication`
     *   to get the address for the vehicle. You should access the location
     *   from the car object and feed it to the Maps service. */
    Location location = mapsClient.getAddress(car.getLocation());
    
    /* TODO: Set the location of the vehicle, including the address information
     * Note: The Location class file also uses @transient for the address,
     * meaning the Maps service needs to be called each time for the address. */
    car.getLocation().setAddress(location.getAddress());
    car.getLocation().setCity(location.getCity());
    car.getLocation().setState(location.getState());
    car.getLocation().setZip(location.getZip());
    
    return car;
  }
  
  /**
   * Either creates or updates a vehicle, based on prior existence of car
   * @param car A car object, which can be either new or existing
   * @return the new/updated car is stored in the repository
   */
  public Car save(Car car) {
    if (car.getId() != null) {
      return carRepository.findById(car.getId())
               .map(carToBeUpdated -> {
                 carToBeUpdated.setDetails(car.getDetails());
                 carToBeUpdated.setLocation(car.getLocation());
                 return carRepository.save(carToBeUpdated);
               }).orElseThrow(CarNotFoundException::new);
    }
    return carRepository.save(car);
  }
  
  /**
   * Deletes a given car by ID
   * @param carId the ID number of the car to delete
   */
  public boolean delete(Long carId) {
    /**
     * TODO: Find the car by ID from the `repository` if it exists.
     *   If it does not exist, throw a CarNotFoundException
     */
    Optional<Car> carQuery = carRepository.findById(carId);
    if (!carQuery.isPresent()) {
      throw new CarNotFoundException("Car not found by id: " + carId);
    }
    Car car = carQuery.get();
    
    /**
     * TODO: Delete the car from the repository.
     */
    carRepository.delete(car);
    return true;
  }
}
