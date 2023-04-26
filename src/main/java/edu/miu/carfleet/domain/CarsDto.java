package edu.miu.carfleet.domain;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author bazz
 * Apr 22 2023
 * 15:55
 */
public class CarsDto {

    private Collection<CarDto> cars;

    public CarsDto(Collection<CarDto> cars) {
        this.cars = cars;
    }

    public Collection<CarDto> getCars() {
        return cars;
    }

    public void setCars(Collection<CarDto> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("----------------------------------------------------------------------------------------\n");
        sb.append(String.format("| %-16s | %-11s | %-15s | %-10s | %-10s |\n", "License Plate", "Type", "Brand", "Price", "Available"));
        sb.append("|------------------|-------------|-----------------|------------|------------|\n");

        for (CarDto car : cars) {
            sb.append(String.format("| %-16s | %-11s | %-15s | %-10s | %-10s |\n", car.getLicensePlate(), car.getType(), car.getBrand(), car.getPrice(), car.getAvailable()));
        }

        sb.append("----------------------------------------------------------------------------------------\n");

        return sb.toString();
    }
}
