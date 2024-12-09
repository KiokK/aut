package by.kiok.motorshow.services;

import by.kiok.motorshow.dtos.req.CarDtoReq;
import by.kiok.motorshow.dtos.resp.CarInfoDto;
import by.kiok.motorshow.dtos.resp.CarPageDto;
import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CarService {

    CarInfoDto findCarById(Long id);

    List<Car> findCarByParams(CarBrand carBrand, LocalDate yearOfProduction, CarCategory category,
                              BigDecimal minPrice, BigDecimal maxPrice);

    List<Car> findCarsSortedByPriceAsc();

    CarPageDto findAllCars(Pageable pageable);

    CarInfoDto createCar(CarDtoReq carDto);

    CarInfoDto updateCar(long id, CarDtoReq carDtoReq);

    void deleteCarById(Long id);

    boolean assignCarToShowroom(long id, long idShowroom);
}
