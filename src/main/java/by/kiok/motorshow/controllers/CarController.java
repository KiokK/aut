package by.kiok.motorshow.controllers;

import by.kiok.motorshow.dtos.req.CarDtoReq;
import by.kiok.motorshow.dtos.resp.CarInfoDto;
import by.kiok.motorshow.dtos.resp.CarPageDto;
import by.kiok.motorshow.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarInfoDto> createCar(@Valid @RequestBody CarDtoReq carDtoReq) {
        CarInfoDto createdCar = carService.createCar(carDtoReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdCar);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarInfoDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @GetMapping
    public ResponseEntity<CarPageDto> getAll(Pageable pageable) {
        return ResponseEntity.ok(carService.findAllCars(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarInfoDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarDtoReq carDtoReq) {
        CarInfoDto updateCar = carService.updateCar(id, carDtoReq);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(updateCar);
    }

    @PutMapping("/{id}/assign/carShowroom/{id}")
    public ResponseEntity<Void> assignCarToShowroom(@PathVariable Long id, @PathVariable Long idCarShowroom) {
        boolean isOk = carService.assignCarToShowroom(id, idCarShowroom);

        return ResponseEntity.status(isOk ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
