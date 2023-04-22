package edu.miu.carfleet.repository;

import edu.miu.carfleet.domain.Car;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author bazz
 * Apr 22 2023
 * 15:58
 */
@Repository
public interface CarRepository extends MongoRepository<Car, String> {

    List<Car> findAllByBrand(String brand);
    List<Car> findAllByPrice(BigDecimal price);

    List<Car> findAllByType(String type);
    Long countAllByBrandAndType(String brand, String type);
    Long countAllByAvailableAndBrandAndType(Boolean avaialbale,String brand, String type);
}
