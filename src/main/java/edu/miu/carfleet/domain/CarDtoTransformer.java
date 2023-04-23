package edu.miu.carfleet.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bazz
 * Apr 22 2023
 * 16:04
 */
public class CarDtoTransformer {

    public static CarDto transformCarToDto(Car car) {
        return new CarDto(car.getLicensePlate(), car.getType(), car.getBrand(), car.getPrice(),car.getAvailable());
    }
    public static Car transformCarDtoToCar(CarDto car) {
        return new Car(car.getLicensePlate(), car.getType(), car.getBrand(), car.getPrice(),car.getAvailable());
    }

    public static CarsDto transformCarsToDto(List<Car> cars) {
        List<CarDto> carDtoList = new ArrayList<>();
        cars.forEach(v -> {
            carDtoList.add(transformCarToDto(v));
        });
        return new CarsDto(carDtoList);
    }
}
