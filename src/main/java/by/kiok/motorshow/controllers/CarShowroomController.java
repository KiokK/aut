package by.kiok.motorshow.controllers;

import by.kiok.motorshow.dtos.req.CarShowroomDtoReq;
import by.kiok.motorshow.dtos.resp.CarShowroomInfoDto;
import by.kiok.motorshow.dtos.resp.CarShowroomPageDto;
import by.kiok.motorshow.services.CarShowroomService;
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
@RequestMapping("/api/carshowrooms")
@RequiredArgsConstructor
public class CarShowroomController {

    private final CarShowroomService showroomService;

    @PostMapping
    public ResponseEntity<CarShowroomInfoDto> createCarShowroom(@Valid @RequestBody CarShowroomDtoReq carShowroomDtoReq) {
        CarShowroomInfoDto created = showroomService.createCarShowroom(carShowroomDtoReq);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarShowroomInfoDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(showroomService.findById(id));
    }

    @GetMapping
    public ResponseEntity<CarShowroomPageDto> getAll(Pageable pageable) {
        return ResponseEntity.ok(showroomService.findAllCarShowrooms(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarShowroomInfoDto> updateCarShowroom(@PathVariable Long id, @Valid @RequestBody CarShowroomDtoReq carShowroomDtoReq) {
        CarShowroomInfoDto updated = showroomService.updateCarShowroom(id, carShowroomDtoReq);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarShowroomById(@PathVariable Long id) {
        showroomService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
