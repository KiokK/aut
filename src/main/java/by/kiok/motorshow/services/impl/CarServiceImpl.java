package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;
import by.kiok.motorshow.services.CarService;
import by.kiok.motorshow.utils.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CarServiceImpl implements CarService {
    
    @Override
    public Car findCarById(Long id) {
        Car car = null;
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            car = session.get(Car.class, id);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return car;
    }

    @Override
    public List<Car> findCarByParams(CarBrand brandCar, LocalDate yearOfProduction, CarCategory category,
                                BigDecimal minPrice, BigDecimal maxPrice) {
        List<Car> cars = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();

            String hqlQuery = """
                    FROM Car c WHERE c.brandCar = :brandCar
                     AND c.yearOfProduction = :yearOfProduction
                     AND c.categories.carCategory = :category
                     AND CAST(c.price AS DOUBLE) BETWEEN :minPrice AND :maxPrice""";

            Query<Car> carQuery = session.createQuery(hqlQuery, Car.class);
            carQuery.setParameter("brandCar", brandCar);
            carQuery.setParameter("yearOfProduction", yearOfProduction);
            carQuery.setParameter("category", category);
            carQuery.setParameter("minPrice", Double.parseDouble(String.valueOf(minPrice)));
            carQuery.setParameter("maxPrice", Double.parseDouble(String.valueOf(maxPrice)));

            cars = carQuery.getResultList();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public List<Car> findCarsSortedByPriceAsc() {
        List<Car> sortedCars = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> root = criteriaQuery.from(Car.class);

            List<Car> cars = session.createQuery(criteriaQuery).getResultList();

            sortedCars = cars.stream()
                    .filter(car -> car.getPrice() != null)
                    .sorted(Comparator.comparing(Car::getPrice))
                    .toList();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return sortedCars;
    }

    @Override
    public List<Car> findCarsSortedByPriceDesc() {
        List<Car> cars = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Car> criteriaQuery = criteriaBuilder.createQuery(Car.class);
            Root<Car> root = criteriaQuery.from(Car.class);
            cars = session.createQuery(criteriaQuery).getResultList();

            cars = cars.stream()
                    .filter(car -> car.getPrice() != null)
                    .sorted((car1, car2) -> car2.getPrice().compareTo(car1.getPrice()))
                    .toList();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public List<Car> findAllCars(int pageNumber, int pageSize) {
        List<Car> cars = new ArrayList<>();
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();

            String hql = """
                    SELECT DISTINCT c FROM Car c
                     JOIN FETCH c.categories
                     JOIN FETCH c.showroom""";
            Query<Car> query = session.createQuery(hql, Car.class);
            query.setFirstResult((pageNumber - 1) * pageSize);
            query.setMaxResults(pageSize);

            cars = query.list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return cars;
    }

    @Override
    public Car createCar(Car car) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return car;
    }

    @Override
    public void updateCar(Car carUpdate, long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Car car = session.get(Car.class, id);

            car.setModel(carUpdate.getModel());
            car.setPrice(carUpdate.getPrice());
            car.setBrandCar(carUpdate.getBrandCar());
            car.setYearOfProduction(carUpdate.getYearOfProduction());

            session.merge(car);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCarById(Long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Car car = session.find(Car.class, id);
            session.remove(car);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void assignCarToShowroom(Car car, CarShowroom showroom) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Car carAssign = session.get(Car.class, car.getId());
            carAssign.setShowroom(showroom);
            session.update(carAssign);

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
