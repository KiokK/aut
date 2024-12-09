package by.kiok.motorshow.dtos.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CarDtoReq {

    @NotBlank
    public String model;

    @NotBlank
    public String brandCar;

    @NotNull
    public LocalDate yearOfProduction;

    @Min(0)
    public BigDecimal price;
}
