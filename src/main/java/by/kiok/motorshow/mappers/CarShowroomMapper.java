package by.kiok.motorshow.mappers;

import by.kiok.motorshow.dtos.req.CarShowroomDtoReq;
import by.kiok.motorshow.dtos.resp.CarShowroomInfoDto;
import by.kiok.motorshow.dtos.resp.CarShowroomPageDto;
import by.kiok.motorshow.models.CarShowroom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring", uses = CarMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CarShowroomMapper {

    CarShowroomInfoDto toCarShowroomInfoDto(CarShowroom carShowroom);

    @Mapping(target="pageNumber", source = "pageable.pageNumber")
    @Mapping(target="pageSize", source = "pageable.pageSize")
    CarShowroomPageDto toCarShowroomPageDto(List<CarShowroom> carShowrooms, Pageable pageable);

    CarShowroom toCarShowroom(CarShowroomDtoReq carShowroomDtoReq);
}
