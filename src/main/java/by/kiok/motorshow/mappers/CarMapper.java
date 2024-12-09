package by.kiok.motorshow.mappers;

import by.kiok.motorshow.dtos.req.CarDtoReq;
import by.kiok.motorshow.dtos.resp.CarInfoDto;
import by.kiok.motorshow.dtos.resp.CarPageDto;
import by.kiok.motorshow.models.Car;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarMapper {

    @Mapping(target = "brandCar", source = "brandCar.description")
    CarInfoDto toCarInfoDto(Car car);

    Car toCar(CarDtoReq carReq);

    CarPageDto toCarPageDto(List<Car> cars, int pageSize, int pageNumber);

}
