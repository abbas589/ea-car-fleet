package edu.miu.carfleet.service;

import edu.miu.carfleet.domain.CarDtoTransformer;
import edu.miu.carfleet.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author bazz
 * Apr 25 2023
 * 16:00
 */
@EnableScheduling
@Component
public class CarCronSchedule {

    Logger logger = LoggerFactory.getLogger(CarCronSchedule.class);

    @Autowired
    private CarRepository carRepository;


    @Scheduled(fixedRate = 20, initialDelay = 2, timeUnit = TimeUnit.SECONDS)
    public void printCarDetails(){
//        logger.info("Printing Car Information ========================= ");
        logger.info(CarDtoTransformer
                .transformCarsToDto(carRepository.findAll()).toString());
//        logger.info("Printing Car Information ========================= ");
    }
}
