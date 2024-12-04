package by.kiok.motorshow.controllers;

import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;
import by.kiok.motorshow.services.CarShowroomService;
import by.kiok.motorshow.services.impl.CarShowroomServiceImpl;
import by.kiok.motorshow.utils.TestEntityFactory;
import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.services.CarService;
import by.kiok.motorshow.services.impl.CarServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Controller {

    public static CarService carService = new CarServiceImpl();
    public static CarShowroomService carShowroomService = new CarShowroomServiceImpl();

    public static void printList(List<Car> cars) {
        cars.forEach(car -> System.out.println(car.getBrandCar() + " " + car.getYearOfProduction() +
                " " + car.getCategories().getCarCategory() + " " + car.getPrice()));
    }

    public static void main(String[] args) {
        // Create car----------------------------
        System.out.println(
                carService.createCar(TestEntityFactory.getCar()));

        // Find All pageable----------------------------
//        carService.findAllCars(1, 5);

        // Assign CarShowroom with Car----------------------------
//        carShowroomService.findAllCarShowrooms();
//        Car car = new Car().setId(1L);
//        CarShowroom carShowroom = new CarShowroom().setId(1L);
//        carService.assignCarToShowroom(car, carShowroom);

        // Find by params----------------------------
//        CarBrand carBrand = CarBrand.BMW;
//        LocalDate year = LocalDate.of(2007,10,13);
//        CarCategory category = CarCategory.ELECTRIC;
//        BigDecimal minPrice = BigDecimal.valueOf(100000);
//        BigDecimal maxPrice = BigDecimal.valueOf(1000000);
//        List<Car> cars = carService.findCarByParams(carBrand, year, category, minPrice, maxPrice);
//        printList(cars);

        // Find All Asc----------------------------
//        List<Car> cars = carService.findCarsSortedByPriceAsc();
//        printList(cars);
//
        // Find All Desc----------------------------
//       List<Car> cars =  carService.findCarsSortedByPriceDesc();
//        printList(cars);

        // Update car----------------------------
//        carService.updateCar(TestEntityFactory.getCar(), 1L);

        // Delete car----------------------------
//        carService.deleteCarById(1L);

    }
}
