package by.kiok.motorshow.services;

import by.kiok.motorshow.models.CarShowroom;

import java.util.List;

public interface CarShowroomService {

    CarShowroom createCarShowroom(CarShowroom carShowroom);

    void removeCarShowroom(Long id);

    void updateCarShowroom(CarShowroom carShowroom, Long id);

    List<CarShowroom> findAllCarShowrooms();
}
