package edu.miu.carfleet.service;

import edu.miu.carfleet.domain.Car;
import edu.miu.carfleet.domain.CarDto;
import edu.miu.carfleet.domain.CarDtoTransformer;
import edu.miu.carfleet.domain.CarsDto;
import edu.miu.carfleet.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * @author bazz
 * Apr 22 2023
 * 15:49
 */
@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    @Transactional
    public CarDto createCar(Car car) {

        car = carRepository.save(car);
        System.out.println("carrr "+car);
        return CarDtoTransformer.transformCarToDto(car);
    }

    @Override
    @Transactional
    public void removeCar(Car car) {
        carRepository.delete(car);
    }

    @Override
    @Transactional
    public CarDto updateCar(Car car, CarDto newCarDto) {
        if (newCarDto.getBrand() != null) {
            car.setBrand(newCarDto.getBrand());
        }
        if (newCarDto.getPrice() != null) {
            car.setPrice(newCarDto.getPrice());
        }
        if (newCarDto.getType() != null) {
            car.setType(newCarDto.getType());
        }
        return CarDtoTransformer.transformCarToDto(carRepository.save(car));
    }

    @Override
    public CarsDto searchCar(String searchType, String searchValue) {
        List<Car> carList = switch (searchType.toLowerCase()) {
            case "type" -> carRepository.findAllByType(searchValue);
            case "brand" -> carRepository.findAllByBrand(searchValue);
            case "price" -> carRepository.findAllByPrice(BigDecimal.valueOf(Long.parseLong(searchValue)));
            default -> Collections.emptyList();
        };
        return CarDtoTransformer.transformCarsToDto(carList);
    }

    @Override
    public Long getAvailableCount(String brand, String type) {
        return carRepository.countAllByAvailableAndBrandAndType(true, brand, type);
    }
}
