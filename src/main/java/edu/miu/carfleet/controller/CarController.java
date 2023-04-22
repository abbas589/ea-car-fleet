package edu.miu.carfleet.controller;

import edu.miu.carfleet.domain.Car;
import edu.miu.carfleet.domain.CarDto;
import edu.miu.carfleet.domain.CarError;
import edu.miu.carfleet.repository.CarRepository;
import edu.miu.carfleet.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author bazz
 * Apr 22 2023
 * 16:09
 */
@RestController
@RequestMapping("/car")
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarRepository carRepository;

    @PostMapping("")
    public ResponseEntity<?> createCar(@RequestBody CarDto carDto) {
        Optional<Car> optionalCar = carRepository.findById(carDto.getLicensePlate());
        if (optionalCar.isPresent()) {
            return new ResponseEntity<>(new CarError("Sorry, a Car with this plate number already exists."), HttpStatus.CONFLICT);
        }
        carService.createCar(carDto);
        return new ResponseEntity<>(carDto, HttpStatus.CREATED);
    }

    @PutMapping("/{licensePlate}/update")
    public ResponseEntity<?> updateCar(@PathVariable("licensePlate") String licensePlate, @RequestBody CarDto dto) {
        Optional<Car> optionalCar = carRepository.findById(licensePlate);
        if (optionalCar.isEmpty()) {
            return new ResponseEntity<>(new CarError("Sorry, a Car with this plate number does not exists."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(carService.updateCar(optionalCar.get(), dto), HttpStatus.OK);
    }

    @DeleteMapping("/{licensePlate}/delete")
    public ResponseEntity<?> deleteCar(@PathVariable("licensePlate") String licensePlate) {
        Optional<Car> optionalCar = carRepository.findById(licensePlate);
        if (optionalCar.isEmpty()) {
            return new ResponseEntity<>(new CarError("Sorry, a Car with this plate number does not exists."), HttpStatus.NOT_FOUND);
        }
        carService.removeCar(optionalCar.get());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchCar(@RequestParam("searchType") String searchType, @RequestParam("searchValue") String searchValue) {
        return new ResponseEntity<>(carService.searchCar(searchType, searchValue), HttpStatus.OK);
    }

    @GetMapping("/get-car-count")
    public ResponseEntity<?> getCarCountByBradAndType(@RequestParam("brand") String brand, @RequestParam("type") String type) {
        return new ResponseEntity<>(carService.getAvailableCount(brand, type), HttpStatus.OK);
    }
}
