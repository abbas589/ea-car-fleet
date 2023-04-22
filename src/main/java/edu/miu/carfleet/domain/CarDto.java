package edu.miu.carfleet.domain;

import java.math.BigDecimal;

/**
 * @author bazz
 * Apr 22 2023
 * 15:55
 */
public class CarDto {

    private String licensePlate;

    private String type;
    private String brand;
    private BigDecimal price;

    public CarDto() {
    }

    public CarDto(String licensePlate, String type, String brand, BigDecimal price) {
        this.licensePlate = licensePlate;
        this.type = type;
        this.brand = brand;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
