package edu.miu.carfleet.service;

import edu.miu.carfleet.domain.Car;
import edu.miu.carfleet.domain.CarDto;
import edu.miu.carfleet.domain.CarsDto;
import edu.miu.carfleet.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author bazz
 * Apr 22 2023
 * 17:39
 */


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarServiceTest {


    @TestConfiguration
    static class CarServiceTestConfiguration {
        @Bean
        public CarService carService() {
            return new CarServiceImpl();
        }
    }

    @Autowired
    private CarService carService;

    @MockBean
    private CarRepository carRepository;

    private Car car1;
    private CarDto carDto1;
    private CarDto carDto2;
    @BeforeEach
    void setUp() {
        car1 = new Car("ABC001", "Camry", "Toyota", BigDecimal.valueOf(400.00), true);
        Car car2 = new Car("ABC002", "Benz", "Mercedes", BigDecimal.valueOf(200.00), true);
        Car car3 = new Car("ABC003", "406", "Peugeot", BigDecimal.valueOf(100.00), true);
        Car car4 = new Car("ABC004", "Passat", "Volkswagon", BigDecimal.valueOf(40.00), true);
        Car car5 = new Car("ABC004", "Corolla", "Toyota", BigDecimal.valueOf(40.00), true);

        carDto1 = new CarDto("ABC001", "Camry", "Toyota", BigDecimal.valueOf(400.00), true);
        carDto2 = new CarDto("ABC002", "Benz", "Mercedes", BigDecimal.valueOf(200.00), true);
        CarDto carDto3 = new CarDto("ABC003", "406", "Peugeot", BigDecimal.valueOf(100.00), true);
        CarDto carDto4 = new CarDto("ABC004", "Passat", "Volkswagon", BigDecimal.valueOf(40.00), true);

        Mockito.when(carRepository.save(car1)).thenReturn(car1);
        Mockito.when(carRepository.findAllByBrand("Toyota")).thenReturn(List.of(car1,car5));
        Mockito.when(carRepository.findAllByPrice(BigDecimal.valueOf(500))).thenReturn(Collections.emptyList());
        Mockito.when(carRepository.findAllByPrice(BigDecimal.valueOf(400))).thenReturn(List.of(car1));
        Mockito.when(carRepository.findAllByType("406")).thenReturn(List.of(car3));
        Mockito.when(carRepository.countAllByAvailableAndBrandAndType(true,"Peugeot","406")).thenReturn(5L);
//        Mockito.when(carRepository.delete(car1)).thenReturn(car1);

    }

    @Test
    void testThatCanCreateCar() {
        CarDto car = carService.createCar(car1);
        assertThat(car.getType()).isEqualTo("Chev");
        assertThat(car.getBrand()).isEqualTo("Toyota");
        assertThat(car.getPrice()).isEqualTo(BigDecimal.valueOf(400.00));
        assertThat(car.getLicensePlate()).isEqualTo("ABC001");
        assertThat(car.getAvailable()).isEqualTo(true);
}

//    @Test
//    void testThatCanRemoveCar() {
//        carService.removeCar(car1);
//    }

    @Test
    void testThatCanUpdateCar() {
        CarDto carDto = carService.updateCar(car1, carDto2);

        assertThat(carDto.getBrand()).isEqualTo(carDto2.getBrand());
        assertThat(carDto.getType()).isEqualTo(carDto2.getType());
        assertThat(carDto.getPrice()).isEqualTo(carDto2.getPrice());
    }

    @Test
    void searchCarByToyotaBrand() {
        CarsDto carsDto = carService.searchCar("brand", "Toyota");
        assertThat(carsDto.getCars()).hasSize(2);
        assertThat(carsDto.getCars().stream().findFirst().get().getBrand()).isEqualTo(Optional.of(carDto1).get().getBrand());

    }

    @Test
    void getAvailableCount() {
        Long peugeotCount = carService.getAvailableCount("Peugeot", "406");
        assertThat(peugeotCount).isEqualTo(5);
    }
}
