package by.kiok.motorshow.services;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CarService {

    Car findCarById(Long id);

    List<Car> findCarByParams(CarBrand carBrand, LocalDate yearOfProduction, CarCategory category,
                              BigDecimal minPrice, BigDecimal maxPrice);

    List<Car> findCarsSortedByPriceAsc();

    List<Car> findCarsSortedByPriceDesc();

    List<Car> findAllCars(int pageNumber, int pageSize);

    Car createCar(Car car);

    void updateCar(Car car, long id);

    void deleteCarById(Long id);

    void assignCarToShowroom(Car car, CarShowroom showroom);
}
