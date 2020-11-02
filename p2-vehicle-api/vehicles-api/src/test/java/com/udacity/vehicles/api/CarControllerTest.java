package com.udacity.vehicles.api;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willCallRealMethod;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udacity.vehicles.client.maps.MapsClient;
import com.udacity.vehicles.client.prices.PriceClient;
import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.service.CarService;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Assertions;
import org.springframework.test.web.servlet.MvcResult;

/**
 * Implements testing of the CarController class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
//@WebMvcTest(CarController.class)
public class CarControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;
  
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private JacksonTester<Car> json;

    @MockBean
    private CarService carService;

    @MockBean
    private PriceClient priceClient;

    @MockBean
    private MapsClient mapsClient;

    /**
     * Creates pre-requisites for testing, such as an example car.
     */
    @Before
    public void setup() {
        Car car = getCar();
        car.setId(1L);
//        given(carService.save(any())).willReturn(car);
        given(carService.findById(any())).willReturn(car);
        given(carService.list()).willReturn(Collections.singletonList(car));

        /*
          Adding this code to mock deleting a car.
          Udacity did not provide a way to delete a car because
          of the `given` mocks above always returning the `car` object in memory
          despite calling the carService.delete method.
        */
        when(carService.delete(any())).thenAnswer(new Answer<Boolean>() {
          public Boolean answer(InvocationOnMock invocation) throws Throwable {
            given(carService.findById(any())).willReturn(null);
            given(carService.list()).willReturn(Collections.EMPTY_LIST);
            return true;
          }
        });
        
        /*
          Adding this code to mock updating a car.
          Udacity did not provide a way to update a car because
          of the `given` mocks above always returning the `car` object in memory
          despite calling the carService.save method.
        */
      when(carService.save(any())).thenAnswer(new Answer<Car>() {
        public Car answer(InvocationOnMock invocation) throws Throwable {
          Car updateCar = invocation.getArgument(0);
          car.setCondition(updateCar.getCondition());
          car.setDetails(updateCar.getDetails());
          return car;
        }
      });
    }

    /**
     * Tests for successful creation of new car in the system
     * @throws Exception when car creation fails in the system
     */
    @Test
    public void createCar() throws Exception {
        Car car = getCar();
        mockMvc.perform(
                post(new URI("/cars"))
                        .content(json.write(car).getJson())
                        .contentType(APPLICATION_JSON_UTF8)
                        .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    /**
     * Tests if the read operation appropriately returns a list of vehicles.
     * @throws Exception if the read operation of the vehicle list fails
     */
    @Test
    public void listCars() throws Exception {
        /* TODO: Add a test to check that the `get` method works by calling
         *   the whole list of vehicles. This should utilize the car from `getCar()`
         *   below (the vehicle will be the first in the list).
         */
      ResponseEntity<HashMap> response =
        this.restTemplate.getForEntity("http://localhost:" + port + "/cars", HashMap.class);
      Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
      verify(carService, times(1)).list();
      List<Car> carsList = (List<Car>) ((HashMap) response.getBody().get("_embedded")).get("carList");
      Assertions.assertEquals(carsList.size(), 1);
    }

    /**
     * Tests the read operation for a single car by ID.
     * @throws Exception if the read operation for a single car fails
     */
    @Test
    public void findCar() throws Exception {
        /* TODO: Add a test to check that the `get` method works by calling
         *   a vehicle by ID. This should utilize the car from `getCar()` below.
         */
        ResponseEntity<Car> response =
          this.restTemplate.getForEntity("http://localhost:" + port + "/cars/1", Car.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Car car = response.getBody();
        assert car != null;
        Assertions.assertEquals(car.getId(), 1);
    }
  
    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void updateCar() throws Exception {
      /* TODO: Add a test to check whether a vehicle is appropriately deleted
       *   when the `delete` method is called from the Car Controller. This
       *   should utilize the car from `getCar()` below.
       */
      JSONObject requestBody = new JSONObject();
      JSONObject updateDetails = new JSONObject();
      JSONObject updateLocation = new JSONObject();
      updateDetails.put("numberOfDoors", 2);
      updateDetails.put("body", "medium");
      updateDetails.put("model", "Malibu");
      JSONObject updatManufacturer = new JSONObject();
      updatManufacturer.put("name", "Chevrolet");
      updatManufacturer.put("code", 101);
      updateDetails.put("manufacturer", updatManufacturer);
      updateLocation.put("lat", 10.789456);
      updateLocation.put("lon", 11.654987);
      requestBody.put("details", updateDetails);
      requestBody.put("location", updateLocation);
      requestBody.put("id", 1);
      requestBody.put("condition", "NEW");
      String requestJson = requestBody.toJSONString();
      
      MvcResult mvcResult = mockMvc.perform(
        put("http://localhost:" + port + "/cars/1")
          .contentType(APPLICATION_JSON_UTF8)
          .content(requestJson)
      )
      .andExpect(status().isOk())
      .andReturn();
  
      ResponseEntity<Car> response =
        this.restTemplate.getForEntity("http://localhost:" + port + "/cars/1", Car.class);
      Car car = response.getBody();
      assert car != null;
      Assertions.assertEquals(car.getDetails().getNumberOfDoors(), 2);
      Assertions.assertEquals(car.getCondition(), Condition.NEW);
    }

    /**
     * Tests the deletion of a single car by ID.
     * @throws Exception if the delete operation of a vehicle fails
     */
    @Test
    public void deleteCar() throws Exception {
        /* TODO: Add a test to check whether a vehicle is appropriately deleted
         *   when the `delete` method is called from the Car Controller. This
         *   should utilize the car from `getCar()` below.
         */
        mockMvc.perform(delete("http://localhost:" + port + "/cars/1"))
          .andExpect(status().isOk())
          .andReturn();
        List<Car> carsList = carService.list();
        Assertions.assertEquals(carsList.size(), 0);
    }

    /**
     * Creates an example Car object for use in testing.
     * @return an example Car object
     */
    private Car getCar() {
        Car car = new Car();
        car.setLocation(new Location(40.730610, -73.935242));
        Details details = new Details();
        Manufacturer manufacturer = new Manufacturer(101, "Chevrolet");
        details.setManufacturer(manufacturer);
        details.setModel("Impala");
        details.setMileage(32280);
        details.setExternalColor("white");
        details.setBody("sedan");
        details.setEngine("3.6L V6");
        details.setFuelType("Gasoline");
        details.setModelYear(2018);
        details.setProductionYear(2018);
        details.setNumberOfDoors(4);
        car.setDetails(details);
        car.setCondition(Condition.USED);
        return car;
    }
}
