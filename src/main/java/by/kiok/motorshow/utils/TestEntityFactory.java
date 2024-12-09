package by.kiok.motorshow.utils;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.Category;
import by.kiok.motorshow.models.Client;
import by.kiok.motorshow.models.Review;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class TestEntityFactory {

    public static Car getCar() {
        return new Car()
                .setBrandCar(CarBrand.BMW)
                .setModel("X3")
                .setYearOfProduction(LocalDate.of(2018, 12, 1))
                .setPrice(BigDecimal.valueOf(100001));
    }

    public static Review createReview() {
        return new Review()
                .setReviewText("Good car with great ...")
                .setRank(8);
    }

    public static CarShowroom getCarShowroom() {
        return new CarShowroom()
                .setName("Salon")
                .setAddress("Surganova, 91");
    }

    public static Client createClient() {
        Set<String> contacts = new HashSet<>();
        contacts.add("+375331234567");
        contacts.add("test@mail.ru");
        contacts.add("Surganova, 30");
        return new Client()
                .setName("Ivanov Ivan")
                .setContact(contacts)
                .setDateOfRegistration(LocalDate.now());
    }

    public static Category getCategory() {
        return new Category()
                .setCarCategory(CarCategory.ELECTRIC);
    }
}
