package by.kiok.motorshow.services.impl;

import by.kiok.motorshow.dtos.req.CarShowroomDtoReq;
import by.kiok.motorshow.dtos.resp.CarShowroomInfoDto;
import by.kiok.motorshow.dtos.resp.CarShowroomPageDto;
import by.kiok.motorshow.mappers.CarShowroomMapper;
import by.kiok.motorshow.models.CarShowroom;
import by.kiok.motorshow.reposirories.CarShowroomRepository;
import by.kiok.motorshow.services.CarShowroomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarShowroomServiceImpl implements CarShowroomService {

    private final CarShowroomMapper showroomMapper;
    private final CarShowroomRepository carShowroomRepository;

    @Override
    @Transactional
    public CarShowroomInfoDto createCarShowroom(CarShowroomDtoReq carShowroomDtoReq) {
        CarShowroom carShowroom = showroomMapper.toCarShowroom(carShowroomDtoReq);
        carShowroom = carShowroomRepository.save(carShowroom);

        return showroomMapper.toCarShowroomInfoDto(carShowroom);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        carShowroomRepository.deleteById(id);
    }

    @Override
    @Transactional
    public CarShowroomInfoDto updateCarShowroom(Long id, CarShowroomDtoReq carShowroomDtoReq) {
        carShowroomRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        CarShowroom newVals = showroomMapper.toCarShowroom(carShowroomDtoReq);
        newVals.setId(id);
        newVals = carShowroomRepository.save(newVals);

        return showroomMapper.toCarShowroomInfoDto(newVals);
    }

    @Override
    public CarShowroomPageDto findAllCarShowrooms(Pageable pageable) {
        Page<CarShowroom> page = carShowroomRepository.findAll(pageable);

        return showroomMapper.toCarShowroomPageDto(page.getContent(), pageable);
    }

    @Override
    public CarShowroomInfoDto findById(long id) {
        CarShowroom carShowroom = carShowroomRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return showroomMapper.toCarShowroomInfoDto(carShowroom);
    }
}
