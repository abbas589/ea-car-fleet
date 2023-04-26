package edu.miu.carfleet.service;

import com.google.gson.Gson;
import edu.miu.carfleet.AppConfiguration;
import edu.miu.carfleet.domain.Car;
import edu.miu.carfleet.domain.CarDto;
import edu.miu.carfleet.domain.CarDtoTransformer;
import edu.miu.carfleet.domain.CarsDto;
import edu.miu.carfleet.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jms.annotation.JmsListener;
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

    Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    AppConfiguration appConfiguration;

    @Autowired
    Gson gson;

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public CarDto createCar(Car car) {

        car = carRepository.save(car);
        logger.info("CREATED CAR {} ", gson.toJson(car));
        return CarDtoTransformer.transformCarToDto(car);
    }

    @Override
    @Transactional
    public void removeCar(Car car) {
        carRepository.delete(car);
        logger.info("DELETED CAR {} ", gson.toJson(car));
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
        if (newCarDto.getAvailable() != null) {
            car.setAvailable(newCarDto.getAvailable());
        }
        logger.info("UPDATED  CAR {} ", gson.toJson(car));

        return CarDtoTransformer.transformCarToDto(carRepository.save(car));
    }

    @Override
    public CarsDto searchCar(String searchType, String value) {
        logger.info("Searching car........");
        List<Car> carList = switch (searchType.toLowerCase()) {
            case "type" -> carRepository.findAllByType(value);
            case "licenseplate" -> Collections.singletonList(carRepository.findById(value).get());
            case "brand" -> carRepository.findAllByBrand(value);
            case "price" -> carRepository.findAllByPrice(BigDecimal.valueOf(Long.parseLong(value)));
            default -> Collections.emptyList();
        };
        return CarDtoTransformer.transformCarsToDto(carList);
    }

    @Override
    public Long getAvailableCount(String brand, String type) {
        Long count = carRepository.countAllByAvailableAndBrandAndType(true, brand, type);
        if (count < 3) {
            logger.info("ABOUT TO PUBLISH EVENT DUE TO LOW COUNT ======= {}", count);
            applicationEventPublisher.publishEvent(new NotificationEvent(String.format("Low Car count for Type %s and Brand %s", type, brand)));
        }
        return count;
    }

    @Override
    @Transactional
    public CarDto reserveCar(String licensePlate) {

        logger.info("Gotten Car Reservation Notification ===================== {}",licensePlate);
        Car car = carRepository.findById(licensePlate).get();

        car.setAvailable(false);
        return CarDtoTransformer.transformCarToDto(carRepository.save(car));
    }

    @Override
    @Transactional
    @JmsListener(destination = "reserve-car")
    public void reserveCarJms(String licensePlate) {

        logger.info("Gotten Car Reservation JMS Notification ===================== {}",licensePlate);
        Car car = carRepository.findById(licensePlate).get();

        car.setAvailable(false);
        carRepository.save(car);
        getAvailableCount(car.getBrand(),car.getType());
    }
}
