package by.kiok.motorshow.services;

import by.kiok.motorshow.dtos.req.CarShowroomDtoReq;
import by.kiok.motorshow.dtos.resp.CarShowroomInfoDto;
import by.kiok.motorshow.dtos.resp.CarShowroomPageDto;
import org.springframework.data.domain.Pageable;

public interface CarShowroomService {

    CarShowroomInfoDto createCarShowroom(CarShowroomDtoReq carShowroomDtoReq);

    void deleteById(Long id);

    CarShowroomInfoDto updateCarShowroom(Long id, CarShowroomDtoReq carShowroomDtoReq);

    CarShowroomPageDto findAllCarShowrooms(Pageable pageable);

    CarShowroomInfoDto findById(long id);
}
