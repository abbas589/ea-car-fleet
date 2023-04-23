package edu.miu.carfleet.service;

import edu.miu.carfleet.domain.Car;
import edu.miu.carfleet.domain.CarDto;
import edu.miu.carfleet.domain.CarsDto;

/**
 * @author bazz
 * Apr 22 2023
 * 15:49
 */
public interface CarService {

    CarDto createCar(Car car);
    void removeCar(Car car);
    CarDto updateCar(Car car, CarDto newCarDto);
    CarsDto searchCar(String searchType, String searchValue);
    Long getAvailableCount(String brand, String type);

}
