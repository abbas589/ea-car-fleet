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
import java.util.Optional;

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
        if(newCarDto.getAvailable()!=null){
            car.setAvailable(newCarDto.getAvailable());
        }
        return CarDtoTransformer.transformCarToDto(carRepository.save(car));
    }

    @Override
    public CarsDto searchCar(String searchType, String value) {
        List<Car> carList = switch (searchType.toLowerCase()) {
            case "type" -> carRepository.findAllByType(value);
            case "brand" -> carRepository.findAllByBrand(value);
            case "price" -> carRepository.findAllByPrice(BigDecimal.valueOf(Long.parseLong(value)));
            default -> Collections.emptyList();
        };
        return CarDtoTransformer.transformCarsToDto(carList);
    }

    @Override
    public Long getAvailableCount(String brand, String type) {
        return carRepository.countAllByAvailableAndBrandAndType(true, brand, type);
    }

    @Override
    @Transactional
    public CarDto reserveCar(Car car) {
        car.setAvailable(false);
        return  CarDtoTransformer.transformCarToDto(carRepository.save(car));
    }
}
