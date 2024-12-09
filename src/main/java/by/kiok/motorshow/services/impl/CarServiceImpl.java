package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.dtos.req.CarDtoReq;
import by.kiok.motorshow.dtos.resp.CarInfoDto;
import by.kiok.motorshow.dtos.resp.CarPageDto;
import by.kiok.motorshow.mappers.CarMapper;
import by.kiok.motorshow.models.Car;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.models.enums.CarBrand;
import by.kiok.motorshow.models.enums.CarCategory;
import by.kiok.motorshow.reposirories.CarRepository;
import by.kiok.motorshow.services.CarService;
import by.kiok.motorshow.utils.HibernateUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper = Mappers.getMapper(CarMapper.class);
    private final CarRepository carRepository;
    
    @Override
    public CarInfoDto findCarById(Long id) {
        Car car = carRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return carMapper.toCarInfoDto(car);
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
    public CarPageDto findAllCars(Pageable pageable) {
        Page<Car> cars = carRepository.findAll(pageable);

        return carMapper.toCarPageDto(cars.getContent(), pageable.getPageSize(), pageable.getPageNumber());
    }

    @Override
    @Transactional
    public CarInfoDto createCar(CarDtoReq carDto) {
        Car car = carMapper.toCar(carDto);
        car = carRepository.save(car);

        return carMapper.toCarInfoDto(car);
    }

    @Override
    @Transactional
    public CarInfoDto updateCar(long id, CarDtoReq carDtoReq) {
        carRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Car newVals = carMapper.toCar(carDtoReq);
        newVals.setId(id);
        newVals = carRepository.save(newVals);

        return carMapper.toCarInfoDto(newVals);
    }

    @Override
    @Transactional
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public boolean assignCarToShowroom(long id, long idShowroom) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            Car carAssign = session.get(Car.class, id);
            CarShowroom showroom = session.get(CarShowroom.class, idShowroom);
            carAssign.setShowroom(showroom);
            session.update(carAssign);

            transaction.commit();

            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return false;
    }
}
